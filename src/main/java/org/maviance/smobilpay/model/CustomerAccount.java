package org.maviance.smobilpay.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Result of {@code GET /v2/validate} — an account-lookup response that
 * reports whether the supplied {@code destination} is recognized by the
 * service and, where available, the associated customer name.
 *
 * <p>The {@link #status} field distinguishes how the account was
 * recognized — see {@link Status} for the three possible outcomes.
 */
public record CustomerAccount(
        @JsonProperty("status") Status status,
        @JsonProperty("name") String name,
        @JsonProperty("destination") String destination
) {
    @JsonCreator
    public CustomerAccount {}

    /**
     * Account-recognition outcome reported by {@code /v2/validate}.
     */
    public enum Status {
        /** Authenticity of the account could be neither verified nor validated. */
        UNKNOWN,
        /** Account syntax has been internally confirmed — e.g. against a regex. */
        VALIDATED,
        /** Account has been positively cross-checked against the service provider. */
        VERIFIED
    }
}
