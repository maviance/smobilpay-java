package org.maviance.smobilpay;

import java.time.Duration;

/**
 * Thrown when an HTTP request to the Smobilpay API exceeds the configured
 * {@link SmobilpayConfig#requestTimeout()} — either while establishing the
 * connection or while awaiting the response.
 *
 * <p>This distinguishes a timeout from other transport failures (DNS lookup,
 * connection refused, TLS handshake) which continue to surface as the base
 * {@link SmobilpayException}. Because it extends {@code SmobilpayException},
 * existing {@code catch (SmobilpayException)} blocks keep working unchanged;
 * callers that want timeout-specific handling (e.g. retry with backoff) can
 * catch this subtype instead.
 */
public final class SmobilpayTimeoutException extends SmobilpayException {

    private static final long serialVersionUID = 1L;

    private final Duration timeout;

    public SmobilpayTimeoutException(String message, Duration timeout, Throwable cause) {
        super(message, cause);
        this.timeout = timeout;
    }

    /** The configured request timeout that was exceeded, or {@code null} if unknown. */
    public Duration timeout() {
        return timeout;
    }
}
