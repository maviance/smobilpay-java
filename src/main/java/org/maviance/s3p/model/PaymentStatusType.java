package org.maviance.s3p.model;

/**
 * Payment processing status.
 *
 * <p>Note: when the request header {@code x-api-version: 3.0.0} is set, the
 * server rewrites {@code SUCCESS} to {@code PENDING} on
 * {@link CollectionResponse#status()}. Subsequent calls to
 * {@code /v2/historystd} or {@code /v2/verifytx} return the final status
 * once the payment clears.
 */
public enum PaymentStatusType {
    REVERSED,
    PENDING,
    ERRORED,
    SUCCESS
}
