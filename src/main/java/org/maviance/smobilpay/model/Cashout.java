package org.maviance.smobilpay.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A cash-out item — a <strong>collection</strong> from a customer's
 * mobile wallet. Money flows <em>out</em> of the customer's wallet into
 * the partner's balance. Returned by {@code /v2/cashout}.
 * {@link #amountType()} is restricted to {@link AmountType#FIXED} or
 * {@link AmountType#CUSTOM}.
 */
public record Cashout(
        @JsonProperty("serviceid") long serviceid,
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
