package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A cash-out item — a disbursement to a recipient's mobile wallet.
 * Returned by {@code /v2/cashout}. {@link #amountType()} is restricted to
 * {@link AmountType#FIXED} or {@link AmountType#CUSTOM}.
 */
public record Cashout(
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
    public Cashout {}
}
