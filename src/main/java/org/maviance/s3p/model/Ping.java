package org.maviance.s3p.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

/**
 * Authenticated probe response from {@code GET /v2/ping}.
 *
 * @param time    current server time and timezone (ISO 8601)
 * @param version server-emitted protocol version ({@code "3.0.0"} when the request {@code x-api-version} header is {@code "3.0.0"}, otherwise {@code "2.2.0"})
 * @param nonce   nonce echoed from the request
 * @param key     public token of the user that sent the request
 */
public record Ping(
        @JsonProperty("time") OffsetDateTime time,
        @JsonProperty("version") String version,
        @JsonProperty("nonce") String nonce,
        @JsonProperty("key") String key
) {
    @JsonCreator
    public Ping {}
}
