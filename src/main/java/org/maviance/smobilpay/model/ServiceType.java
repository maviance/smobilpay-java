package org.maviance.smobilpay.model;

/**
 * Service type — drives which masterdata endpoint produces the corresponding
 * payment items for this service.
 */
public enum ServiceType {
    SEARCHABLE_BILL,
    NON_SEARCHABLE_BILL,
    PRODUCT,
    TOPUP,
    SUBSCRIPTION,
    CASHIN,
    CASHOUT,
    VOUCHER
}
