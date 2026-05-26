package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * A subscription payment item. Returned by {@code /v2/subscription}.
 *
 * <p>Looked up by either {@code serviceNumber} or {@code customerNumber};
 * the result set contains all subscriptions found under the search
 * criteria, each with its own {@link #payItemId()}.
 */
public record Subscription(
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
        @JsonProperty("optNmb") Double optNmb,
        @JsonProperty("serviceNumber") String serviceNumber,
        @JsonProperty("customerReference") String customerReference,
        @JsonProperty("customerName") String customerName,
        @JsonProperty("customerNumber") String customerNumber,
        @JsonProperty("startDate") LocalDate startDate,
        @JsonProperty("dueDate") LocalDate dueDate,
        @JsonProperty("endDate") LocalDate endDate
) implements PaymentItem {
    @JsonCreator
    public Subscription {}
}
