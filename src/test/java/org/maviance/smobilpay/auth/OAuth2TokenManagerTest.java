package org.maviance.smobilpay.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maviance.smobilpay.SmobilpayAuthException;
import org.maviance.smobilpay.SmobilpayConfig;
import org.maviance.smobilpay.SmobilpayException;
import org.maviance.smobilpay.SmobilpayTimeoutException;
import org.maviance.smobilpay.http.JsonMapper;

import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Base64;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OAuth2TokenManagerTest {

    private static final String PUBLIC_KEY = "SMOBILPAYAGENT";
    private static final String SECRET_KEY = "BA875F70-D6EF-633D-0783-D0538F9BAF91";

    private WireMockServer wireMock;
    private HttpClient httpClient;
    private ObjectMapper mapper;
    private SmobilpayConfig config;

    @BeforeEach
    void setUp() {
        wireMock = new WireMockServer(options().dynamicPort());
        wireMock.start();
        httpClient = HttpClient.newHttpClient();
        mapper = JsonMapper.create();
        config = SmobilpayConfig.builder()
                .baseUrl("http://localhost:" + wireMock.port())
                .credentials(PUBLIC_KEY, SECRET_KEY)
                .requestTimeout(Duration.ofSeconds(5))
                .tokenRefreshSkew(Duration.ofSeconds(30))
                .build();
    }

    @AfterEach
    void tearDown() {
        wireMock.stop();
    }

    @Test
    void mintsTokenWithBasicAuthAndFormBody() {
        stubTokenSuccess(3600, "jwt-1");

        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, config);

        String token = manager.accessToken();

        assertThat(token).isEqualTo("jwt-1");
        wireMock.verify(postRequestedFor(urlEqualTo("/oauth/token"))
                .withHeader("Authorization", equalTo(expectedBasicAuthHeader()))
                .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded"))
                .withRequestBody(equalTo("grant_type=client_credentials")));
    }

    @Test
    void cachesTokenAcrossCallsUntilExpiry() {
        stubTokenSuccess(3600, "jwt-cached");
        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, config);

        manager.accessToken();
        manager.accessToken();
        manager.accessToken();

        wireMock.verify(1, postRequestedFor(urlEqualTo("/oauth/token")));
    }

    @Test
    void refreshesExpiredToken() {
        Instant t0 = Instant.parse("2026-05-26T10:00:00Z");
        Clock fakeClock = new MutableClock(t0);

        // First mint: short-lived token (60s)
        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"access_token\":\"first\",\"token_type\":\"Bearer\",\"expires_in\":60}")));

        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, config, fakeClock);
        String first = manager.accessToken();
        assertThat(first).isEqualTo("first");

        // Advance past expiry (60s) — refresh-skew is 30s, so at 35s after issuance the cache should already be considered expired
        ((MutableClock) fakeClock).advance(Duration.ofSeconds(40));
        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"access_token\":\"second\",\"token_type\":\"Bearer\",\"expires_in\":3600}")));

        String second = manager.accessToken();
        assertThat(second).isEqualTo("second");
        wireMock.verify(2, postRequestedFor(urlEqualTo("/oauth/token")));
    }

    @Test
    void refreshForcesMintEvenWhenCacheValid() {
        stubTokenSuccess(3600, "first");
        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, config);
        manager.accessToken();

        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"access_token\":\"forced\",\"token_type\":\"Bearer\",\"expires_in\":3600}")));

        String forced = manager.refresh();
        assertThat(forced).isEqualTo("forced");
        wireMock.verify(2, postRequestedFor(urlEqualTo("/oauth/token")));
    }

    @Test
    void wrapsInvalidClientAs401SmobilpayAuthException() {
        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\":\"invalid_client\"}")));

        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, config);
        assertThatThrownBy(manager::accessToken)
                .isInstanceOf(SmobilpayAuthException.class)
                .satisfies(e -> {
                    SmobilpayAuthException ae = (SmobilpayAuthException) e;
                    assertThat(ae.httpStatus()).isEqualTo(401);
                    assertThat(ae.oauthError()).isEqualTo("invalid_client");
                });
    }

    @Test
    void wrapsUnsupportedGrantTypeAs400() {
        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\":\"unsupported_grant_type\"}")));

        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, config);
        assertThatThrownBy(manager::accessToken)
                .isInstanceOf(SmobilpayAuthException.class)
                .satisfies(e -> {
                    SmobilpayAuthException ae = (SmobilpayAuthException) e;
                    assertThat(ae.httpStatus()).isEqualTo(400);
                    assertThat(ae.oauthError()).isEqualTo("unsupported_grant_type");
                });
    }

    @Test
    void rejectsMissingAccessTokenInResponse() {
        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"token_type\":\"Bearer\",\"expires_in\":3600}")));

        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, config);
        assertThatThrownBy(manager::accessToken)
                .isInstanceOf(SmobilpayAuthException.class)
                .hasMessageContaining("access_token");
    }

    @Test
    void rejectsMissingExpiresInResponse() {
        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"access_token\":\"abc\",\"token_type\":\"Bearer\"}")));

        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, config);
        assertThatThrownBy(manager::accessToken)
                .isInstanceOf(SmobilpayAuthException.class)
                .hasMessageContaining("expires_in");
    }

    @Test
    void rejectsNonJsonResponseBody() {
        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("not json")));

        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, config);
        assertThatThrownBy(manager::accessToken)
                .isInstanceOf(SmobilpayAuthException.class);
    }

    @Test
    void wrapsTokenEndpointTimeoutAsTimeoutException() {
        // Token endpoint takes 3s to respond; the client gives up after 500ms.
        SmobilpayConfig shortTimeout = SmobilpayConfig.builder()
                .baseUrl("http://localhost:" + wireMock.port())
                .credentials(PUBLIC_KEY, SECRET_KEY)
                .requestTimeout(Duration.ofMillis(500))
                .build();
        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(3000)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"access_token\":\"abc\",\"token_type\":\"Bearer\",\"expires_in\":3600}")));

        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, shortTimeout);

        assertThatThrownBy(manager::accessToken)
                .isInstanceOf(SmobilpayTimeoutException.class)
                .isInstanceOf(SmobilpayException.class)
                .satisfies(t -> assertThat(((SmobilpayTimeoutException) t).timeout())
                        .isEqualTo(Duration.ofMillis(500)));
    }

    @Test
    void cachedTokenStartsNull() {
        OAuth2TokenManager manager = new OAuth2TokenManager(httpClient, mapper, config);
        assertThat(manager.cachedToken()).isNull();
    }

    private void stubTokenSuccess(int expiresIn, String token) {
        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"access_token\":\"" + token + "\",\"token_type\":\"Bearer\",\"expires_in\":"
                                + expiresIn + "}")));
    }

    private String expectedBasicAuthHeader() {
        String creds = PUBLIC_KEY + ":" + SECRET_KEY;
        return "Basic " + Base64.getEncoder().encodeToString(creds.getBytes(StandardCharsets.UTF_8));
    }

    private static final class MutableClock extends Clock {
        private Instant now;

        MutableClock(Instant start) { this.now = start; }

        void advance(Duration d) { now = now.plus(d); }

        @Override public ZoneOffset getZone() { return ZoneOffset.UTC; }
        @Override public Clock withZone(java.time.ZoneId zone) { return this; }
        @Override public Instant instant() { return now; }
    }
}
