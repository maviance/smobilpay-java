package org.maviance.s3p.api;

import org.maviance.s3p.http.HttpTransport;
import org.maviance.s3p.model.CollectionRequest;
import org.maviance.s3p.model.CollectionResponse;

import java.util.Objects;

/**
 * Execute payment collections against a previously-issued quote. Backed by
 * the partner spec {@code Confirm} tag.
 */
public final class ConfirmApi {

    private final HttpTransport transport;

    public ConfirmApi(HttpTransport transport) {
        this.transport = transport;
    }

    /**
     * {@code POST /v2/collectstd} — execute a payment collection against
     * a valid (unexpired) quote.
     *
     * <p>A {@code 498} response indicates the quote has expired; re-quote
     * before retrying.
     */
    public CollectionResponse collect(CollectionRequest request) {
        Objects.requireNonNull(request, "request");
        return transport.post("/v2/collectstd", request, CollectionResponse.class);
    }
}
