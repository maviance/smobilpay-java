package org.maviance.smobilpay;

import java.net.URI;
import java.time.Duration;
import java.util.Objects;

/**
 * Immutable configuration for an {@link SmobilpayClient}.
 *
 * <p>Holds the partner base URL, OAuth 2.0 client credentials ({@code publicKey}
 * / {@code secretKey}), the {@code x-api-version} header value, and per-request
 * timeouts. The base URL, credentials, and callback registration are issued by
 * Maviance support during partner onboarding.
 *
 * <p>Use {@link #builder()} to construct an instance.
 */
public final class SmobilpayConfig {

    /** Default x-api-version header value mandated by the partner spec. */
    public static final String DEFAULT_API_VERSION = "3.0.0";

    /** Default per-request timeout. */
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

    private final URI baseUrl;
    private final String publicKey;
    private final String secretKey;
    private final String apiVersion;
    private final Duration requestTimeout;
    private final Duration tokenRefreshSkew;

    private SmobilpayConfig(Builder b) {
        this.baseUrl = Objects.requireNonNull(b.baseUrl, "baseUrl is required");
        this.publicKey = Objects.requireNonNull(b.publicKey, "publicKey is required");
        this.secretKey = Objects.requireNonNull(b.secretKey, "secretKey is required");
        this.apiVersion = b.apiVersion;
        this.requestTimeout = b.requestTimeout;
        this.tokenRefreshSkew = b.tokenRefreshSkew;
    }

    public URI baseUrl() {
        return baseUrl;
    }

    public String publicKey() {
        return publicKey;
    }

    public String secretKey() {
        return secretKey;
    }

    public String apiVersion() {
        return apiVersion;
    }

    public Duration requestTimeout() {
        return requestTimeout;
    }

    /**
     * Refresh-ahead window. Tokens within this many seconds of expiry are
     * treated as already-expired so the client mints a fresh one before the
     * next request fails. Defaults to 30 seconds.
     */
    public Duration tokenRefreshSkew() {
        return tokenRefreshSkew;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private URI baseUrl;
        private String publicKey;
        private String secretKey;
        private String apiVersion = DEFAULT_API_VERSION;
        private Duration requestTimeout = DEFAULT_TIMEOUT;
        private Duration tokenRefreshSkew = Duration.ofSeconds(30);

        private Builder() {}

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = URI.create(Objects.requireNonNull(baseUrl, "baseUrl"));
            return this;
        }

        public Builder baseUrl(URI baseUrl) {
            this.baseUrl = Objects.requireNonNull(baseUrl, "baseUrl");
            return this;
        }

        public Builder credentials(String publicKey, String secretKey) {
            this.publicKey = Objects.requireNonNull(publicKey, "publicKey");
            this.secretKey = Objects.requireNonNull(secretKey, "secretKey");
            return this;
        }

        public Builder apiVersion(String apiVersion) {
            this.apiVersion = Objects.requireNonNull(apiVersion, "apiVersion");
            return this;
        }

        public Builder requestTimeout(Duration requestTimeout) {
            this.requestTimeout = Objects.requireNonNull(requestTimeout, "requestTimeout");
            return this;
        }

        public Builder tokenRefreshSkew(Duration tokenRefreshSkew) {
            this.tokenRefreshSkew = Objects.requireNonNull(tokenRefreshSkew, "tokenRefreshSkew");
            return this;
        }

        public SmobilpayConfig build() {
            return new SmobilpayConfig(this);
        }
    }
}
