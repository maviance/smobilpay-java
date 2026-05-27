package org.maviance.smobilpay;

/**
 * Base unchecked exception for all Smobilpay client failures. Concrete subtypes
 * carry additional context: {@link SmobilpayApiException} for HTTP-error responses
 * with a parseable {@code Error} envelope, and {@link SmobilpayAuthException} for
 * OAuth 2.0 token issuance failures.
 */
public class SmobilpayException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SmobilpayException(String message) {
        super(message);
    }

    public SmobilpayException(String message, Throwable cause) {
        super(message, cause);
    }
}
