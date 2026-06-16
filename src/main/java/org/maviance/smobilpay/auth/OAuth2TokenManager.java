package org.maviance.smobilpay.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.maviance.smobilpay.SmobilpayAuthException;
import org.maviance.smobilpay.SmobilpayConfig;
import org.maviance.smobilpay.SmobilpayException;
import org.maviance.smobilpay.SmobilpayTimeoutException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;

/**
 * Mints, caches, and refreshes OAuth 2.0 access tokens via the Smobilpay
 * {@code POST /oauth/token} endpoint using the {@code client_credentials}
 * grant.
 *
 * <p>Tokens are cached in memory and reused until {@code now + skew >=
 * expiresAt}, then a fresh one is minted. Thread-safe: concurrent callers
 * see a single in-flight mint via {@code synchronized}.
 */
public final class OAuth2TokenManager {

    private static final String TOKEN_PATH = "/oauth/token";
    private static final String GRANT_BODY = "grant_type=client_credentials";

    private final HttpClient httpClient;
    private final ObjectMapper jsonMapper;
    private final SmobilpayConfig config;
    private final Clock clock;

    private volatile OAuth2Token current;

    public OAuth2TokenManager(HttpClient httpClient, ObjectMapper jsonMapper, SmobilpayConfig config) {
        this(httpClient, jsonMapper, config, Clock.systemUTC());
    }

    /** Constructor for tests — accepts an injectable clock. */
    public OAuth2TokenManager(HttpClient httpClient,
                              ObjectMapper jsonMapper,
                              SmobilpayConfig config,
                              Clock clock) {
        this.httpClient = httpClient;
        this.jsonMapper = jsonMapper;
        this.config = config;
        this.clock = clock;
    }

    /**
     * Returns a valid bearer token, minting one if the cache is empty or
     * the cached token is within {@link SmobilpayConfig#tokenRefreshSkew()} of
     * expiry. Blocks while minting; safe for concurrent callers.
     */
    public String accessToken() {
        OAuth2Token snapshot = current;
        Instant now = clock.instant();
        if (snapshot != null && !snapshot.isExpired(now, config.tokenRefreshSkew())) {
            return snapshot.accessToken();
        }
        return mintAndCache();
    }

    /** Forces a fresh mint, replacing any cached token. Exposed for diagnostics. */
    public synchronized String refresh() {
        OAuth2Token minted = mintToken(clock.instant());
        current = minted;
        return minted.accessToken();
    }

    /** Current cached token, if any. {@code null} until first {@link #accessToken()} call. */
    public OAuth2Token cachedToken() {
        return current;
    }

    private synchronized String mintAndCache() {
        OAuth2Token snapshot = current;
        Instant now = clock.instant();
        if (snapshot != null && !snapshot.isExpired(now, config.tokenRefreshSkew())) {
            return snapshot.accessToken();
        }
        OAuth2Token minted = mintToken(now);
        current = minted;
        return minted.accessToken();
    }

    private OAuth2Token mintToken(Instant issuedAt) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(tokenUri())
                .timeout(config.requestTimeout())
                .header("Authorization", basicAuthHeader())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GRANT_BODY))
                .build();

        HttpResponse<String> resp;
        try {
            resp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (HttpTimeoutException e) {
            // Covers connect and response timeouts on the token endpoint. Surface
            // as the same typed subtype as API-call timeouts so a single
            // catch (SmobilpayTimeoutException) covers every timeout source.
            throw new SmobilpayTimeoutException(
                    "OAuth token request timed out after " + config.requestTimeout()
                            + ": " + e.getMessage(), config.requestTimeout(), e);
        } catch (IOException e) {
            throw new SmobilpayAuthException(0, null,
                    "Failed to call /oauth/token: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SmobilpayAuthException(0, null,
                    "Interrupted while calling /oauth/token", e);
        }

        int status = resp.statusCode();
        String body = resp.body();
        if (status < 200 || status >= 300) {
            String oauthError = tryReadOAuthErrorCode(body);
            throw new SmobilpayAuthException(status, oauthError,
                    "OAuth token mint failed (HTTP " + status + ")"
                            + (oauthError != null ? ", error=" + oauthError : "")
                            + (body == null || body.isEmpty() ? "" : ": " + body));
        }

        return parseTokenResponse(body, issuedAt);
    }

    private OAuth2Token parseTokenResponse(String body, Instant issuedAt) {
        JsonNode node;
        try {
            node = jsonMapper.readTree(body);
        } catch (IOException e) {
            throw new SmobilpayAuthException(200, null,
                    "OAuth token response was not valid JSON: " + e.getMessage(), e);
        }
        JsonNode accessTokenNode = node.get("access_token");
        JsonNode expiresInNode = node.get("expires_in");
        if (accessTokenNode == null || accessTokenNode.isNull() || accessTokenNode.asText().isEmpty()) {
            throw new SmobilpayAuthException(200, null,
                    "OAuth token response missing required field 'access_token'");
        }
        if (expiresInNode == null || !expiresInNode.canConvertToLong()) {
            throw new SmobilpayAuthException(200, null,
                    "OAuth token response missing required field 'expires_in'");
        }
        String tokenType = node.hasNonNull("token_type") ? node.get("token_type").asText() : "Bearer";
        long expiresIn = expiresInNode.asLong();
        Instant expiresAt = issuedAt.plusSeconds(expiresIn);
        return new OAuth2Token(accessTokenNode.asText(), tokenType, expiresAt);
    }

    private String tryReadOAuthErrorCode(String body) {
        if (body == null || body.isEmpty()) {
            return null;
        }
        try {
            JsonNode n = jsonMapper.readTree(body);
            if (n.hasNonNull("error")) {
                return n.get("error").asText();
            }
        } catch (IOException ignored) {
            // body wasn't JSON
        }
        return null;
    }

    private URI tokenUri() {
        String base = config.baseUrl().toString();
        if (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        try {
            return new URI(base + TOKEN_PATH);
        } catch (URISyntaxException e) {
            throw new SmobilpayException("Invalid OAuth token URI: " + base + TOKEN_PATH, e);
        }
    }

    private String basicAuthHeader() {
        String creds = config.publicKey() + ":" + config.secretKey();
        String encoded = Base64.getEncoder().encodeToString(creds.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encoded;
    }
}
