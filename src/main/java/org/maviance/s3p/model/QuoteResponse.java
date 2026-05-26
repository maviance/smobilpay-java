package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Quote response from {@code POST /v2/quotestd}. The {@link #quoteId()}
 * must be passed back on a {@link CollectionRequest} within
 * {@link #expiresAt()}.
 */
public record QuoteResponse(
        @JsonProperty("quoteId") UUID quoteId,
        @JsonProperty("expiresAt") OffsetDateTime expiresAt,
        @JsonProperty("payItemId") String payItemId,
        @JsonProperty("amountLocalCur") Float amountLocalCur,
        @JsonProperty("priceLocalCur") Float priceLocalCur,
        @JsonProperty("priceSystemCur") Float priceSystemCur,
        @JsonProperty("localCur") String localCur,
        @JsonProperty("systemCur") String systemCur,
        @JsonProperty("promotion") String promotion
) {
    @JsonCreator
    public QuoteResponse {}
}
