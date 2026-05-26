package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Current state of a previously-issued payment collection. Returned by
 * {@code /v2/historystd} and {@code /v2/verifytx}.
 *
 * <p>Note: {@link #serviceid()} is a string here even though
 * {@link Service#serviceid()} is an int — this matches the partner spec.
 */
public record PaymentStatus(
        @JsonProperty("ptn") String ptn,
        @JsonProperty("serviceid") String serviceid,
        @JsonProperty("merchant") String merchant,
        @JsonProperty("timestamp") OffsetDateTime timestamp,
        @JsonProperty("receiptNumber") String receiptNumber,
        @JsonProperty("veriCode") String veriCode,
        @JsonProperty("clearingDate") LocalDate clearingDate,
        @JsonProperty("trid") String trid,
        @JsonProperty("priceLocalCur") Float priceLocalCur,
        @JsonProperty("priceSystemCur") Float priceSystemCur,
        @JsonProperty("localCur") String localCur,
        @JsonProperty("systemCur") String systemCur,
        @JsonProperty("pin") String pin,
        @JsonProperty("status") PaymentStatusType status,
        @JsonProperty("payItemId") String payItemId,
        @JsonProperty("payItemDescr") String payItemDescr,
        @JsonProperty("errorCode") int errorCode,
        @JsonProperty("tag") String tag,
        @JsonProperty("commission") Commission commission
) {
    @JsonCreator
    public PaymentStatus {}
}
