package org.maviance.smobilpay.auth;

import java.time.Duration;
import java.time.Instant;

/**
 * Cached OAuth 2.0 bearer token. Immutable; {@link OAuth2TokenManager}
 * replaces the instance on refresh.
 *
 * @param accessToken signed JWT to send as {@code Authorization: Bearer}
 * @param tokenType   token type returned by the server (always {@code "Bearer"} in practice)
 * @param expiresAt   absolute expiry instant (request issuance time + {@code expires_in} seconds)
 */
public record OAuth2Token(String accessToken, String tokenType, Instant expiresAt) {

    /** True if {@code now + skew} is at or past {@link #expiresAt}. */
    public boolean isExpired(Instant now, Duration skew) {
        return !now.plus(skew).isBefore(expiresAt);
    }
}
