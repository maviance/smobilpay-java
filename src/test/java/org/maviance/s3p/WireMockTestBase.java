package org.maviance.s3p;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * Spins a WireMock server on a dynamic port and exposes a freshly-configured
 * {@link S3pClient} that talks to it. Subclasses can register additional
 * stubs in their tests; the OAuth token endpoint is pre-stubbed to return a
 * valid JWT-shaped response.
 */
public abstract class WireMockTestBase {

    protected static final String PUBLIC_KEY = "test-public-key";
    protected static final String SECRET_KEY = "test-secret-key";
    protected static final String FAKE_TOKEN = "eyJfakeAccessToken";

    protected WireMockServer wireMock;
    protected S3pClient client;
    protected S3pConfig config;

    @BeforeEach
    void startWireMock() {
        wireMock = new WireMockServer(options().dynamicPort());
        wireMock.start();
        stubTokenEndpoint();
        config = S3pConfig.builder()
                .baseUrl("http://localhost:" + wireMock.port())
                .credentials(PUBLIC_KEY, SECRET_KEY)
                .requestTimeout(Duration.ofSeconds(5))
                .build();
        client = S3pClient.create(config);
    }

    @AfterEach
    void stopWireMock() {
        if (client != null) {
            client.close();
        }
        if (wireMock != null) {
            wireMock.stop();
        }
    }

    protected void stubTokenEndpoint() {
        wireMock.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"access_token\":\"" + FAKE_TOKEN
                                + "\",\"token_type\":\"Bearer\",\"expires_in\":3600}")));
    }
}
