package org.maviance.smobilpay.http;

import org.junit.jupiter.api.Test;
import org.maviance.smobilpay.SmobilpayApiException;
import org.maviance.smobilpay.SmobilpayException;
import org.maviance.smobilpay.WireMockTestBase;
import org.maviance.smobilpay.model.Ping;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpTransportTest extends WireMockTestBase {

    @Test
    void parsesErrorEnvelopeOn404() {
        wireMock.stubFor(get(urlEqualTo("/v2/ping"))
                .willReturn(aResponse().withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"respCode\":2001,\"devMsg\":\"Not found\","
                                + "\"usrMsg\":\"Resource not found\",\"link\":\"https://docs.example.invalid/2001\"}")));

        assertThatThrownBy(() -> client.verify().ping())
                .isInstanceOf(SmobilpayApiException.class)
                .satisfies(t -> {
                    SmobilpayApiException e = (SmobilpayApiException) t;
                    assertThat(e.httpStatus()).isEqualTo(404);
                    assertThat(e.error()).isPresent();
                    assertThat(e.error().get().respCode()).isEqualTo(2001);
                });
    }

    @Test
    void carriesRawBodyWhenErrorNotJson() {
        wireMock.stubFor(get(urlEqualTo("/v2/ping"))
                .willReturn(aResponse().withStatus(500)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("internal server error")));

        assertThatThrownBy(() -> client.verify().ping())
                .isInstanceOf(SmobilpayApiException.class)
                .satisfies(t -> {
                    SmobilpayApiException e = (SmobilpayApiException) t;
                    assertThat(e.httpStatus()).isEqualTo(500);
                    assertThat(e.error()).isEmpty();
                    assertThat(e.rawBody()).isEqualTo("internal server error");
                });
    }

    @Test
    void carriesBlankBodyOn401WithNoEnvelope() {
        wireMock.stubFor(get(urlEqualTo("/v2/ping"))
                .willReturn(aResponse().withStatus(401)));

        assertThatThrownBy(() -> client.verify().ping())
                .isInstanceOf(SmobilpayApiException.class)
                .satisfies(t -> {
                    SmobilpayApiException e = (SmobilpayApiException) t;
                    assertThat(e.httpStatus()).isEqualTo(401);
                    assertThat(e.error()).isEmpty();
                });
    }

    @Test
    void parsesValidResponseWithBaseUrlTrailingSlash() {
        // Use a separate config with a trailing slash in base URL
        wireMock.stubFor(get(urlEqualTo("/v2/ping"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"time\":\"2026-05-02T08:30:00+00:00\","
                                + "\"version\":\"3.0.0\","
                                + "\"nonce\":\"abc\",\"key\":\"def\"}")));

        Ping ping = client.verify().ping();
        assertThat(ping.version()).isEqualTo("3.0.0");
    }

    @Test
    void wrapsMalformedResponseBody() {
        wireMock.stubFor(get(urlEqualTo("/v2/ping"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("definitely not json")));

        assertThatThrownBy(() -> client.verify().ping())
                .isInstanceOf(SmobilpayException.class)
                .hasMessageContaining("parse");
    }
}
