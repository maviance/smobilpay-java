package org.maviance.s3p.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.maviance.s3p.http.HttpTransport;
import org.maviance.s3p.http.QueryParams;
import org.maviance.s3p.model.Bill;
import org.maviance.s3p.model.QuoteRequest;
import org.maviance.s3p.model.QuoteResponse;
import org.maviance.s3p.model.Subscription;

import java.util.List;
import java.util.Objects;

/**
 * Lookups and quotes that prepare a payment collection. Backed by the
 * partner spec {@code Initiate} tag.
 */
public final class InitiateApi {

    private final HttpTransport transport;

    public InitiateApi(HttpTransport transport) {
        this.transport = transport;
    }

    /**
     * {@code GET /v2/bill} — search bills for a service number. For
     * {@code SEARCHABLE_BILL} services this returns every open bill; for
     * {@code NON_SEARCHABLE_BILL} services it returns a single item.
     */
    public List<Bill> bills(String merchant, int serviceid, String serviceNumber) {
        Objects.requireNonNull(merchant, "merchant");
        Objects.requireNonNull(serviceNumber, "serviceNumber");
        return transport.get("/v2/bill",
                QueryParams.of()
                        .add("merchant", merchant)
                        .add("serviceid", serviceid)
                        .add("serviceNumber", serviceNumber),
                new TypeReference<List<Bill>>() {});
    }

    /**
     * {@code GET /v2/subscription} — search subscriptions by service number
     * and/or customer number. Exactly one of them must be supplied (the
     * server rejects calls with neither).
     */
    public List<Subscription> subscriptions(String merchant,
                                            String serviceid,
                                            String serviceNumber,
                                            String customerNumber) {
        Objects.requireNonNull(merchant, "merchant");
        Objects.requireNonNull(serviceid, "serviceid");
        if (serviceNumber == null && customerNumber == null) {
            throw new IllegalArgumentException(
                    "either serviceNumber or customerNumber must be provided");
        }
        return transport.get("/v2/subscription",
                QueryParams.of()
                        .add("merchant", merchant)
                        .add("serviceid", serviceid)
                        .add("serviceNumber", serviceNumber)
                        .add("customerNumber", customerNumber),
                new TypeReference<List<Subscription>>() {});
    }

    /**
     * {@code POST /v2/quotestd} — request a price quote for a payment
     * collection. Quotes expire after a few minutes and must be requested
     * fresh before each collection.
     */
    public QuoteResponse quote(QuoteRequest request) {
        Objects.requireNonNull(request, "request");
        return transport.post("/v2/quotestd", request, QuoteResponse.class);
    }
}
