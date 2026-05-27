package org.maviance.smobilpay.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.maviance.smobilpay.http.JsonMapper;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CollectionRequestTest {

    private final ObjectMapper mapper = JsonMapper.create();

    @Test
    void builderRequiresMandatoryFields() {
        UUID quoteId = UUID.randomUUID();
        assertThatThrownBy(() -> CollectionRequest.builder(null, "237699999999", "x@example.com").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("quoteId");
        assertThatThrownBy(() -> CollectionRequest.builder(quoteId, null, "x@example.com").build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("customerPhonenumber");
        assertThatThrownBy(() -> CollectionRequest.builder(quoteId, "237699999999", null).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("customerEmailaddress");
    }

    @Test
    void tagExceedingFiftyCharactersIsRejected() {
        UUID quoteId = UUID.randomUUID();
        String longTag = "a".repeat(51);
        assertThatThrownBy(() -> CollectionRequest.builder(quoteId, "237699999999", "x@example.com")
                .tag(longTag)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("tag");
    }

    @Test
    void callbackUrlExceeding255CharactersIsRejected() {
        UUID quoteId = UUID.randomUUID();
        String longUrl = "https://example.com/" + "x".repeat(250);
        assertThatThrownBy(() -> CollectionRequest.builder(quoteId, "237699999999", "x@example.com")
                .callbackUrl(longUrl)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("callbackUrl");
    }

    @Test
    void serializesWithoutNullOptionalFields() throws Exception {
        UUID quoteId = UUID.fromString("0e1f7f4a-3b2c-4a8d-9d1f-1f5d2c3a4b6e");
        CollectionRequest request = CollectionRequest.builder(quoteId, "237699999999", "customer@example.com")
                .customerName("Jane Doe")
                .trid("ORDER-2026-05-02-0001")
                .tag("retail-front-desk")
                .build();

        String json = mapper.writeValueAsString(request);
        assertThat(json).contains("\"quoteId\":\"0e1f7f4a-3b2c-4a8d-9d1f-1f5d2c3a4b6e\"");
        assertThat(json).contains("\"customerPhonenumber\":\"237699999999\"");
        assertThat(json).contains("\"customerEmailaddress\":\"customer@example.com\"");
        assertThat(json).contains("\"customerName\":\"Jane Doe\"");
        assertThat(json).contains("\"trid\":\"ORDER-2026-05-02-0001\"");
        assertThat(json).contains("\"tag\":\"retail-front-desk\"");
        assertThat(json).doesNotContain("customerAddress");
        assertThat(json).doesNotContain("callbackUrl");
        assertThat(json).doesNotContain("cdata");
    }

    @Test
    void quoteRequestValidatesAmount() {
        assertThatThrownBy(() -> new QuoteRequest(0, "PI-1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("amount");
        assertThatThrownBy(() -> new QuoteRequest(-5, "PI-1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void quoteRequestRoundTripsThroughJson() throws Exception {
        QuoteRequest request = new QuoteRequest(5000, "PI-ENEO-PREPAID-001");
        String json = mapper.writeValueAsString(request);
        QuoteRequest decoded = mapper.readValue(json, QuoteRequest.class);
        assertThat(decoded).isEqualTo(request);
    }
}
