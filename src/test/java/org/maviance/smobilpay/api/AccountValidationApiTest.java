package org.maviance.smobilpay.api;

import org.junit.jupiter.api.Test;
import org.maviance.smobilpay.WireMockTestBase;
import org.maviance.smobilpay.model.CustomerAccount;

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

    @Test
    void validateAccountReturnsVerifiedAccountWithName() {
        wireMock.stubFor(get(urlEqualTo("/v2/validate?destination=677389120&serviceId=20053"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"VERIFIED\",\"name\":\"Ronaldo MINE\",\"destination\":\"677389120\"}")));

        CustomerAccount account = client.accountValidation().validateAccount("677389120", 20053L);

        assertThat(account.status()).isEqualTo(CustomerAccount.Status.VERIFIED);
        assertThat(account.name()).isEqualTo("Ronaldo MINE");
        assertThat(account.destination()).isEqualTo("677389120");
        wireMock.verify(getRequestedFor(urlEqualTo("/v2/validate?destination=677389120&serviceId=20053")));
    }

    @Test
    void validateAccountReturnsUnknownWhenAccountNotCrossChecked() {
        wireMock.stubFor(get(urlEqualTo("/v2/validate?destination=000000000&serviceId=20053"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"UNKNOWN\",\"destination\":\"000000000\"}")));

        CustomerAccount account = client.accountValidation().validateAccount("000000000", 20053L);

        assertThat(account.status()).isEqualTo(CustomerAccount.Status.UNKNOWN);
        assertThat(account.name()).isNull();
        assertThat(account.destination()).isEqualTo("000000000");
    }

    @Test
    void validateAccountRejectsNullDestination() {
        assertThatThrownBy(() -> client.accountValidation().validateAccount(null, 1L))
                .isInstanceOf(NullPointerException.class);
    }
}
