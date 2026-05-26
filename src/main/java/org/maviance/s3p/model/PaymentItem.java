package org.maviance.s3p.model;

/**
 * Common interface for the payment-item records returned by the masterdata
 * endpoints ({@code /v2/product}, {@code /v2/voucher}, {@code /v2/topup},
 * {@code /v2/cashin}, {@code /v2/cashout}) and the lookup endpoints
 * ({@code /v2/bill}, {@code /v2/subscription}).
 *
 * <p>{@link #payItemId()} is the value the partner passes to
 * {@link QuoteRequest} to request pricing for this item.
 */
public interface PaymentItem {
    int serviceid();
    String merchant();
    String payItemId();
    AmountType amountType();
    String localCur();
    String name();
    Float amountLocalCur();
    String description();
    String payItemDescr();
    String optStrg();
    Double optNmb();
}
