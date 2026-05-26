package org.maviance.s3p.api;

import org.maviance.s3p.http.HttpTransport;
import org.maviance.s3p.http.QueryParams;

import java.util.Objects;

/**
 * Pre-payment account checks. Backed by the partner spec
 * {@code Account Validation} tag.
 */
public final class AccountValidationApi {

    private final HttpTransport transport;

    public AccountValidationApi(HttpTransport transport) {
        this.transport = transport;
    }

    /**
     * {@code GET /v2/verify} — verify that a service number is valid for the
     * selected service. Only meaningful for services that report
     * {@code isVerifiable: true}.
     *
     * @return {@code true} if the service number is valid
     */
    public boolean verifyServiceNumber(String merchant, int serviceid, String serviceNumber) {
        Objects.requireNonNull(merchant, "merchant");
        Objects.requireNonNull(serviceNumber, "serviceNumber");
        Boolean response = transport.get("/v2/verify",
                QueryParams.of()
                        .add("merchant", merchant)
                        .add("serviceid", serviceid)
                        .add("serviceNumber", serviceNumber),
                Boolean.class);
        return Boolean.TRUE.equals(response);
    }
}
