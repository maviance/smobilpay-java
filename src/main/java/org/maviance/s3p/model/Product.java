package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A purchasable product or voucher. Returned by {@code /v2/product} and
 * {@code /v2/voucher}. {@link #amountType()} is restricted to
 * {@link AmountType#FIXED} or {@link AmountType#CUSTOM}.
 *
 * <p>For voucher purchases, the digital code is provided on the
 * {@link CollectionResponse#pin()} field of the successful collection.
 */
public record Product(
        @JsonProperty("serviceid") int serviceid,
        @JsonProperty("merchant") String merchant,
        @JsonProperty("payItemId") String payItemId,
        @JsonProperty("payItemDescr") String payItemDescr,
        @JsonProperty("amountType") AmountType amountType,
        @JsonProperty("localCur") String localCur,
        @JsonProperty("name") String name,
        @JsonProperty("amountLocalCur") Float amountLocalCur,
        @JsonProperty("description") String description,
        @JsonProperty("optStrg") String optStrg,
        @JsonProperty("optNmb") Double optNmb
) implements PaymentItem {
    @JsonCreator
    public Product {}
}
