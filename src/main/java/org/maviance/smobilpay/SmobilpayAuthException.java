package org.maviance.smobilpay;

/**
 * Thrown when OAuth 2.0 token issuance at {@code POST /oauth/token} fails.
 *
 * <p>Common causes per the partner spec:
 * <ul>
 *   <li>{@code 400 unsupported_grant_type} — {@code grant_type} not set to {@code client_credentials}.</li>
 *   <li>{@code 401 invalid_client} — credentials missing, malformed, or rejected.</li>
 *   <li>{@code 502}/{@code 504} — token issuance unavailable / timed out.</li>
 * </ul>
 */
public final class SmobilpayAuthException extends SmobilpayException {

    private static final long serialVersionUID = 1L;

    private final int httpStatus;
    private final String oauthError;

    public SmobilpayAuthException(int httpStatus, String oauthError, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.oauthError = oauthError;
    }

    public SmobilpayAuthException(int httpStatus, String oauthError, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.oauthError = oauthError;
    }

    public int httpStatus() {
        return httpStatus;
    }

    /** OAuth 2.0 standard error identifier (e.g. {@code invalid_client}), or {@code null} if not provided. */
    public String oauthError() {
        return oauthError;
    }
}
