package org.maviance.smobilpay;

import org.maviance.smobilpay.model.ApiError;

import java.util.Optional;

/**
 * Thrown when the Smobilpay API returns a non-2xx response with a parseable
 * {@link ApiError} envelope. Match on {@link ApiError#respCode()} for
 * programmatic handling — that is the canonical machine identifier per
 * the partner spec.
 */
public final class SmobilpayApiException extends SmobilpayException {

    private static final long serialVersionUID = 1L;

    private final int httpStatus;
    private final ApiError error;
    private final String rawBody;

    public SmobilpayApiException(int httpStatus, ApiError error, String rawBody) {
        super(buildMessage(httpStatus, error, rawBody));
        this.httpStatus = httpStatus;
        this.error = error;
        this.rawBody = rawBody;
    }

    public int httpStatus() {
        return httpStatus;
    }

    /**
     * The deserialized {@link ApiError} envelope if the server returned one.
     * Some endpoints return a bare HTTP status code with no body (e.g. 401
     * Unauthorized) — in that case this is {@code Optional.empty()}.
     */
    public Optional<ApiError> error() {
        return Optional.ofNullable(error);
    }

    /** Raw response body for diagnostics when {@link #error()} is empty. */
    public String rawBody() {
        return rawBody;
    }

    private static String buildMessage(int httpStatus, ApiError error, String rawBody) {
        if (error != null) {
            return "Smobilpay API error (HTTP " + httpStatus + ", respCode=" + error.respCode()
                    + "): " + error.devMsg();
        }
        return "Smobilpay API error (HTTP " + httpStatus + "): "
                + (rawBody == null || rawBody.isEmpty() ? "<empty body>" : rawBody);
    }
}
