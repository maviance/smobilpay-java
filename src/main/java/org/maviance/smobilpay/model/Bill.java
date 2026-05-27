package org.maviance.smobilpay.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * A bill payment item. Returned by {@code /v2/bill}.
 *
 * <p>For services with {@link ServiceType#SEARCHABLE_BILL} the response
 * contains all open bills for the provided service number; for
 * {@link ServiceType#NON_SEARCHABLE_BILL} it always contains a single
 * bill.
 */
public record Bill(
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
        @JsonProperty("optNmb") Double optNmb,
        @JsonProperty("billType") BillType billType,
        @JsonProperty("penaltyAmount") Double penaltyAmount,
        @JsonProperty("payOrder") int payOrder,
        @JsonProperty("serviceNumber") String serviceNumber,
        @JsonProperty("billNumber") String billNumber,
        @JsonProperty("customerNumber") String customerNumber,
        @JsonProperty("billMonth") String billMonth,
        @JsonProperty("billYear") String billYear,
        @JsonProperty("billDate") LocalDate billDate,
        @JsonProperty("billDueDate") LocalDate billDueDate
) implements PaymentItem {
    @JsonCreator
    public Bill {}
}
