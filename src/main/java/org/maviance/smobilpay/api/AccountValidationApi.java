package org.maviance.smobilpay.api;

import org.maviance.smobilpay.http.HttpTransport;
import org.maviance.smobilpay.http.QueryParams;
import org.maviance.smobilpay.model.CustomerAccount;

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
    public boolean verifyServiceNumber(String merchant, long serviceid, String serviceNumber) {
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

    /**
     * {@code GET /v2/validate} — validate an account by destination
     * (typically an MSISDN or contract number) and retrieve the associated
     * customer name, when available.
     *
     * <p>Unlike {@link #verifyServiceNumber(String, long, String)}, this
     * call returns a rich {@link CustomerAccount} envelope with a
     * tri-state {@link CustomerAccount.Status} (UNKNOWN, VALIDATED,
     * VERIFIED) — so callers can distinguish a syntactically-correct
     * account from one that has been cross-checked against the provider.
     *
     * <p><strong>Restricted endpoint.</strong> Access is granted only to
     * partners who have cleared the upstream compliance review (KYC /
     * data-protection obligations apply to the returned customer name).
     * Unauthorized callers receive HTTP 401; the client performs one automatic
     * token refresh and retry on a 401, so when the cause is missing clearance
     * the retry returns 401 again and surfaces as a
     * {@code SmobilpayApiException}. Contact your integration manager to
     * request enablement.
     */
    public CustomerAccount validateAccount(String destination, long serviceId) {
        Objects.requireNonNull(destination, "destination");
        return transport.get("/v2/validate",
                QueryParams.of()
                        .add("destination", destination)
                        .add("serviceId", serviceId),
                CustomerAccount.class);
    }
}
