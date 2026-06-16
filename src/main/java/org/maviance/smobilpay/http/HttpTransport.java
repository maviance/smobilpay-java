package org.maviance.smobilpay.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.maviance.smobilpay.SmobilpayApiException;
import org.maviance.smobilpay.SmobilpayConfig;
import org.maviance.smobilpay.SmobilpayException;
import org.maviance.smobilpay.SmobilpayTimeoutException;
import org.maviance.smobilpay.auth.OAuth2TokenManager;
import org.maviance.smobilpay.model.ApiError;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.function.Supplier;

/**
 * Request/response engine for the Smobilpay client.
 *
 * <p>Wraps the JDK {@link HttpClient}. Each call:
 * <ol>
 *   <li>Resolves the request URI from {@code baseUrl + path + query string}.</li>
 *   <li>Asks the {@link OAuth2TokenManager} for a fresh bearer token (which it
 *       caches between calls).</li>
 *   <li>Attaches {@code Authorization: Bearer}, {@code x-api-version}, and
 *       {@code Accept: application/json} headers.</li>
 *   <li>Dispatches the request, applies the configured timeout, and parses
 *       the response body into the requested type, or throws
 *       {@link SmobilpayApiException} on non-2xx.</li>
 * </ol>
 */
public final class HttpTransport {

    private static final String HDR_API_VERSION = "x-api-version";
    private static final String HDR_AUTHORIZATION = "Authorization";
    private static final String HDR_ACCEPT = "Accept";
    private static final String HDR_CONTENT_TYPE = "Content-Type";
    private static final String CT_JSON = "application/json";

    private final HttpClient httpClient;
    private final ObjectMapper jsonMapper;
    private final SmobilpayConfig config;
    private final OAuth2TokenManager tokenManager;

    public HttpTransport(HttpClient httpClient,
                         ObjectMapper jsonMapper,
                         SmobilpayConfig config,
                         OAuth2TokenManager tokenManager) {
        this.httpClient = httpClient;
        this.jsonMapper = jsonMapper;
        this.config = config;
        this.tokenManager = tokenManager;
    }

    public <T> T get(String path, QueryParams query, Class<T> type) {
        return execute(() -> authedRequestBuilder(path, query).GET().build(), type);
    }

    public <T> T get(String path, QueryParams query, TypeReference<T> type) {
        return execute(() -> authedRequestBuilder(path, query).GET().build(), type);
    }

    public <T> T post(String path, Object body, Class<T> type) {
        String json = writeJson(body);
        return execute(() -> authedRequestBuilder(path, QueryParams.of())
                .header(HDR_CONTENT_TYPE, CT_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build(), type);
    }

    /** Raw send (no auth header). Used by the OAuth token manager. */
    public HttpResponse<String> sendRaw(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (HttpTimeoutException e) {
            // Covers both connect timeouts (HttpConnectTimeoutException) and
            // response timeouts. Surface as a typed subtype so callers can
            // distinguish a timeout from other transport failures.
            Duration timeout = config.requestTimeout();
            throw new SmobilpayTimeoutException(
                    "Request timed out after " + timeout + ": " + e.getMessage(), timeout, e);
        } catch (IOException e) {
            throw new SmobilpayException("HTTP transport error: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SmobilpayException("Interrupted while sending HTTP request", e);
        }
    }

    public Duration requestTimeout() {
        return config.requestTimeout();
    }

    private HttpRequest.Builder authedRequestBuilder(String path, QueryParams query) {
        URI uri = resolveUri(path, query);
        String bearer = tokenManager.accessToken();
        return HttpRequest.newBuilder()
                .uri(uri)
                .timeout(config.requestTimeout())
                .header(HDR_AUTHORIZATION, "Bearer " + bearer)
                .header(HDR_API_VERSION, config.apiVersion())
                .header(HDR_ACCEPT, CT_JSON);
    }

    private URI resolveUri(String path, QueryParams query) {
        String base = config.baseUrl().toString();
        if (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        String p = path.startsWith("/") ? path : "/" + path;
        String full = base + p;
        if (query != null && !query.isEmpty()) {
            full += "?" + query.encode();
        }
        try {
            return new URI(full);
        } catch (URISyntaxException e) {
            throw new SmobilpayException("Invalid URI: " + full, e);
        }
    }

    private <T> T execute(Supplier<HttpRequest> request, Class<T> type) {
        HttpResponse<String> resp = sendWithReauth(request);
        return readJson(resp.body(), type);
    }

    private <T> T execute(Supplier<HttpRequest> request, TypeReference<T> type) {
        HttpResponse<String> resp = sendWithReauth(request);
        return readJsonRef(resp.body(), type);
    }

    /**
     * Sends the request, retrying once on a 401 after forcing a token refresh.
     *
     * <p>{@code request} is a supplier because the retry needs a fresh
     * {@link HttpRequest}: a body publisher is single-use and the retry must
     * carry the refreshed bearer. A 401 is rejected at the auth layer before
     * any business logic runs, so retrying is safe even for non-idempotent
     * POSTs. The retry is bounded to one attempt; a persistent 401 (e.g. a
     * restricted endpoint) falls through to {@link #checkSuccess}.
     */
    private HttpResponse<String> sendWithReauth(Supplier<HttpRequest> request) {
        HttpResponse<String> resp = sendRaw(request.get());
        if (resp.statusCode() == 401) {
            tokenManager.refresh();
            resp = sendRaw(request.get());
        }
        checkSuccess(resp);
        return resp;
    }

    private void checkSuccess(HttpResponse<String> resp) {
        int status = resp.statusCode();
        if (status >= 200 && status < 300) {
            return;
        }
        String body = resp.body();
        ApiError error = null;
        if (body != null && !body.isBlank()) {
            try {
                error = jsonMapper.readValue(body, ApiError.class);
            } catch (IOException ignored) {
                // body wasn't an Error envelope — keep raw for diagnostics
            }
        }
        throw new SmobilpayApiException(status, error, body);
    }

    private <T> T readJson(String body, Class<T> type) {
        if (type == Void.class || type == void.class) {
            return null;
        }
        try {
            return jsonMapper.readValue(body, type);
        } catch (IOException e) {
            throw new SmobilpayException("Failed to parse response body: " + e.getMessage(), e);
        }
    }

    private <T> T readJsonRef(String body, TypeReference<T> type) {
        try {
            return jsonMapper.readValue(body, type);
        } catch (IOException e) {
            throw new SmobilpayException("Failed to parse response body: " + e.getMessage(), e);
        }
    }

    private String writeJson(Object body) {
        try {
            return jsonMapper.writeValueAsString(body);
        } catch (IOException e) {
            throw new SmobilpayException("Failed to serialize request body: " + e.getMessage(), e);
        }
    }
}
