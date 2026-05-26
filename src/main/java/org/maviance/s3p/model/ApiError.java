package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Standard S3P error envelope. The {@link #respCode} is the canonical
 * machine identifier — match on this rather than parsing {@link #devMsg}.
 * The full error catalog is provided during partner onboarding.
 *
 * @param respCode unique error response code identifying the issue
 * @param devMsg   verbose, plain-language description for the integrator
 * @param usrMsg   high-level, user-safe error message
 * @param link     URI to documentation for this error code (may be null)
 */
public record ApiError(
        @JsonProperty("respCode") int respCode,
        @JsonProperty("devMsg") String devMsg,
        @JsonProperty("usrMsg") String usrMsg,
        @JsonProperty("link") String link
) {
    @JsonCreator
    public ApiError {}
}
