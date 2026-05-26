package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Commission earned on a transaction. Present on
 * {@link PaymentStatus#commission()} only when the commission feature is
 * enabled for the merchant/service.
 *
 * @param earnings commission amount earned
 * @param currency currency (ISO 4217)
 */
public record Commission(
        @JsonProperty("earnings") Float earnings,
        @JsonProperty("currency") String currency
) {
    @JsonCreator
    public Commission {}
}
