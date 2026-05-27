package org.maviance.smobilpay.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.maviance.smobilpay.http.JsonMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Round-trip tests for {@link CustomerAccount} — the result type of
 * {@code GET /v2/validate}. Pins the three {@link CustomerAccount.Status}
 * wire values and confirms the {@code name} field tolerates omission
 * (it's optional per the partner spec).
 */
class CustomerAccountTest {

    private final ObjectMapper mapper = JsonMapper.create();

    @Test
    void deserializesVerifiedAccountWithName() throws Exception {
        CustomerAccount account = mapper.readValue(
                "{\"status\":\"VERIFIED\",\"name\":\"Ronaldo MINE\",\"destination\":\"677389120\"}",
                CustomerAccount.class);

        assertThat(account.status()).isEqualTo(CustomerAccount.Status.VERIFIED);
        assertThat(account.name()).isEqualTo("Ronaldo MINE");
        assertThat(account.destination()).isEqualTo("677389120");
    }

    @Test
    void deserializesValidatedAccountWithoutName() throws Exception {
        CustomerAccount account = mapper.readValue(
                "{\"status\":\"VALIDATED\",\"destination\":\"677389120\"}",
                CustomerAccount.class);

        assertThat(account.status()).isEqualTo(CustomerAccount.Status.VALIDATED);
        assertThat(account.name()).isNull();
        assertThat(account.destination()).isEqualTo("677389120");
    }

    @Test
    void deserializesUnknownAccount() throws Exception {
        CustomerAccount account = mapper.readValue(
                "{\"status\":\"UNKNOWN\",\"destination\":\"000000000\"}",
                CustomerAccount.class);

        assertThat(account.status()).isEqualTo(CustomerAccount.Status.UNKNOWN);
        assertThat(account.name()).isNull();
        assertThat(account.destination()).isEqualTo("000000000");
    }

    @Test
    void ignoresUnknownPropertiesPerWireForwardCompat() throws Exception {
        CustomerAccount account = mapper.readValue(
                "{\"status\":\"VERIFIED\",\"name\":\"X\",\"destination\":\"123\","
                        + "\"newFieldServerAdded\":\"future\"}",
                CustomerAccount.class);

        assertThat(account.status()).isEqualTo(CustomerAccount.Status.VERIFIED);
        assertThat(account.name()).isEqualTo("X");
    }
}
