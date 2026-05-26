package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

/**
 * Response from {@code POST /v2/collectstd} confirming a payment collection.
 *
 * <p>Note: when the request {@code x-api-version: 3.0.0} header is set, a
 * {@link PaymentStatusType#SUCCESS} status is rewritten to
 * {@link PaymentStatusType#PENDING} server-side. Poll
 * {@code /v2/verifytx} or wait for the {@code callbackUrl} webhook to learn
 * the final status.
 *
 * <p>{@link #ptn()} is globally unique; {@link #receiptNumber()} is bound to
 * the agent context and is not globally unique.
 */
public record CollectionResponse(
        @JsonProperty("ptn") String ptn,
        @JsonProperty("timestamp") OffsetDateTime timestamp,
        @JsonProperty("agentBalance") Float agentBalance,
        @JsonProperty("receiptNumber") String receiptNumber,
        @JsonProperty("veriCode") String veriCode,
        @JsonProperty("priceLocalCur") Float priceLocalCur,
        @JsonProperty("priceSystemCur") Float priceSystemCur,
        @JsonProperty("localCur") String localCur,
        @JsonProperty("systemCur") String systemCur,
        @JsonProperty("trid") String trid,
        @JsonProperty("pin") String pin,
        @JsonProperty("status") PaymentStatusType status,
        @JsonProperty("payItemId") String payItemId,
        @JsonProperty("payItemDescr") String payItemDescr,
        @JsonProperty("tag") String tag
) {
    @JsonCreator
    public CollectionResponse {}
}
