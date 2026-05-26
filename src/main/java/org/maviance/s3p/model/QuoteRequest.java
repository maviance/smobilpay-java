package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Request body for {@code POST /v2/quotestd}.
 *
 * @param amount    amount to be collected in the local currency of the payment item
 *                  (full integer, no decimals; subject to the item's {@link AmountType})
 * @param payItemId payment item id from the masterdata catalog to quote
 */
public record QuoteRequest(
        @JsonProperty("amount") int amount,
        @JsonProperty("payItemId") String payItemId
) {
    @JsonCreator
    public QuoteRequest {
        if (amount < 1) {
            throw new IllegalArgumentException("amount must be >= 1, got " + amount);
        }
        Objects.requireNonNull(payItemId, "payItemId");
    }
}
