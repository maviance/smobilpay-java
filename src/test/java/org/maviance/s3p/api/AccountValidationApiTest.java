package org.maviance.s3p.api;

import org.junit.jupiter.api.Test;
import org.maviance.s3p.WireMockTestBase;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountValidationApiTest extends WireMockTestBase {

    @Test
    void returnsTrueForValidServiceNumber() {
        wireMock.stubFor(get(urlEqualTo("/v2/verify?merchant=ENEO&serviceid=1234&serviceNumber=01234567"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));

        boolean valid = client.accountValidation().verifyServiceNumber("ENEO", 1234, "01234567");

        assertThat(valid).isTrue();
        wireMock.verify(getRequestedFor(urlEqualTo("/v2/verify?merchant=ENEO&serviceid=1234&serviceNumber=01234567")));
    }

    @Test
    void returnsFalseForInvalidServiceNumber() {
        wireMock.stubFor(get(urlEqualTo("/v2/verify?merchant=ENEO&serviceid=1234&serviceNumber=BAD"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("false")));

        boolean valid = client.accountValidation().verifyServiceNumber("ENEO", 1234, "BAD");

        assertThat(valid).isFalse();
    }

    @Test
    void requiresMerchantAndServiceNumber() {
        assertThatThrownBy(() -> client.accountValidation().verifyServiceNumber(null, 1, "x"))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> client.accountValidation().verifyServiceNumber("ENEO", 1, null))
                .isInstanceOf(NullPointerException.class);
    }
}
