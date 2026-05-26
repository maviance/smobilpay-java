package org.maviance.s3p;

/**
 * Base unchecked exception for all S3P client failures. Concrete subtypes
 * carry additional context: {@link S3pApiException} for HTTP-error responses
 * with a parseable {@code Error} envelope, and {@link S3pAuthException} for
 * OAuth 2.0 token issuance failures.
 */
public class S3pException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public S3pException(String message) {
        super(message);
    }

    public S3pException(String message, Throwable cause) {
        super(message, cause);
    }
}
