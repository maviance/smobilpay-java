package org.maviance.s3p.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.maviance.s3p.http.HttpTransport;
import org.maviance.s3p.http.QueryParams;
import org.maviance.s3p.model.Cashin;
import org.maviance.s3p.model.Cashout;
import org.maviance.s3p.model.Merchant;
import org.maviance.s3p.model.Product;
import org.maviance.s3p.model.Service;
import org.maviance.s3p.model.Topup;

import java.util.List;

/**
 * Static reference data: merchants, services, and the payment-item catalogs
 * needed to drive a payment UI. Backed by the partner spec {@code Masterdata}
 * tag.
 */
public final class MasterdataApi {

    private final HttpTransport transport;

    public MasterdataApi(HttpTransport transport) {
        this.transport = transport;
    }

    /** {@code GET /v2/merchant} — every merchant supported by the system. */
    public List<Merchant> merchants() {
        return transport.get("/v2/merchant", QueryParams.of(),
                new TypeReference<List<Merchant>>() {});
    }

    /** {@code GET /v2/service} — every service supported by the system. */
    public List<Service> services() {
        return transport.get("/v2/service", QueryParams.of(),
                new TypeReference<List<Service>>() {});
    }

    /**
     * {@code GET /v2/product} — purchasable products, optionally filtered by
     * service id.
     */
    public List<Product> products(Integer serviceid) {
        return transport.get("/v2/product",
                QueryParams.of().add("serviceid", serviceid),
                new TypeReference<List<Product>>() {});
    }

    /**
     * {@code GET /v2/voucher} — purchasable vouchers, optionally filtered by
     * service id. The digital code is delivered on
     * {@code CollectionResponse.pin} on a successful collection.
     */
    public List<Product> vouchers(Integer serviceid) {
        return transport.get("/v2/voucher",
                QueryParams.of().add("serviceid", serviceid),
                new TypeReference<List<Product>>() {});
    }

    /** {@code GET /v2/topup} — top-up packages, optionally filtered by service id. */
    public List<Topup> topups(Integer serviceid) {
        return transport.get("/v2/topup",
                QueryParams.of().add("serviceid", serviceid),
                new TypeReference<List<Topup>>() {});
    }

    /** {@code GET /v2/cashin} — cash-in packages, optionally filtered by service id. */
    public List<Cashin> cashins(Integer serviceid) {
        return transport.get("/v2/cashin",
                QueryParams.of().add("serviceid", serviceid),
                new TypeReference<List<Cashin>>() {});
    }

    /** {@code GET /v2/cashout} — cash-out packages, optionally filtered by service id. */
    public List<Cashout> cashouts(Integer serviceid) {
        return transport.get("/v2/cashout",
                QueryParams.of().add("serviceid", serviceid),
                new TypeReference<List<Cashout>>() {});
    }
}
