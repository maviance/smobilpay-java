package org.maviance.smobilpay.auth;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class OAuth2TokenTest {

    @Test
    void freshTokenIsNotExpired() {
        Instant now = Instant.parse("2026-05-26T10:00:00Z");
        OAuth2Token token = new OAuth2Token("abc", "Bearer", now.plusSeconds(3600));
        assertThat(token.isExpired(now, Duration.ofSeconds(30))).isFalse();
    }

    @Test
    void tokenWithinSkewIsExpired() {
        Instant now = Instant.parse("2026-05-26T10:00:00Z");
        OAuth2Token token = new OAuth2Token("abc", "Bearer", now.plusSeconds(20));
        assertThat(token.isExpired(now, Duration.ofSeconds(30))).isTrue();
    }

    @Test
    void tokenAtExpiryIsExpired() {
        Instant now = Instant.parse("2026-05-26T10:00:00Z");
        OAuth2Token token = new OAuth2Token("abc", "Bearer", now);
        assertThat(token.isExpired(now, Duration.ZERO)).isTrue();
    }

    @Test
    void tokenJustBeforeSkewBoundaryIsNotExpired() {
        Instant now = Instant.parse("2026-05-26T10:00:00Z");
        OAuth2Token token = new OAuth2Token("abc", "Bearer", now.plusSeconds(31));
        assertThat(token.isExpired(now, Duration.ofSeconds(30))).isFalse();
    }
}
