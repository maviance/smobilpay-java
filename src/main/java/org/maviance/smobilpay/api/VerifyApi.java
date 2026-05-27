package org.maviance.smobilpay.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.maviance.smobilpay.http.HttpTransport;
import org.maviance.smobilpay.http.QueryParams;
import org.maviance.smobilpay.model.Account;
import org.maviance.smobilpay.model.PaymentStatus;
import org.maviance.smobilpay.model.Ping;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Status and account verification endpoints. Backed by the partner spec
 * {@code Verify} tag.
 */
public final class VerifyApi {

    private final HttpTransport transport;

    public VerifyApi(HttpTransport transport) {
        this.transport = transport;
    }

    /**
     * {@code GET /v2/ping} — authenticated round-trip probe. Returns the
     * server time, version, the request nonce, and the public token used to
     * authenticate the request.
     */
    public Ping ping() {
        return transport.get("/v2/ping", QueryParams.of(), Ping.class);
    }

    /**
     * {@code GET /v2/account} — the authenticated agent's account profile.
     */
    public Account account() {
        return transport.get("/v2/account", QueryParams.of(), Account.class);
    }

    /**
     * {@code GET /v2/verifytx} — current status of a payment collection by
     * {@code ptn} and/or {@code trid}. At least one parameter must be
     * provided.
     */
    public List<PaymentStatus> verifyTransaction(String ptn, String trid) {
        if (ptn == null && trid == null) {
            throw new IllegalArgumentException("at least one of ptn or trid must be provided");
        }
        return transport.get("/v2/verifytx",
                QueryParams.of().add("ptn", ptn).add("trid", trid),
                new TypeReference<List<PaymentStatus>>() {});
    }

    /**
     * {@code GET /v2/historystd} by PTN — search history by exact payment
     * transaction number.
     */
    public List<PaymentStatus> historyByPtn(String ptn) {
        if (ptn == null) {
            throw new IllegalArgumentException("ptn is required");
        }
        return transport.get("/v2/historystd",
                QueryParams.of().add("ptn", ptn),
                new TypeReference<List<PaymentStatus>>() {});
    }

    /**
     * {@code GET /v2/historystd} by TRID — search history by custom
     * transaction reference.
     */
    public List<PaymentStatus> historyByTrid(String trid) {
        if (trid == null) {
            throw new IllegalArgumentException("trid is required");
        }
        return transport.get("/v2/historystd",
                QueryParams.of().add("trid", trid),
                new TypeReference<List<PaymentStatus>>() {});
    }

    /**
     * {@code GET /v2/historystd} by date range — search history by an
     * inclusive date range.
     */
    public List<PaymentStatus> historyByDateRange(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("both from and to dates are required");
        }
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("to date is before from date");
        }
        return transport.get("/v2/historystd",
                QueryParams.of()
                        .add("timestamp_from",
                                from.atStartOfDay(ZoneOffset.UTC)
                                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                        .add("timestamp_to",
                                to.atTime(23, 59, 59).atOffset(ZoneOffset.UTC)
                                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)),
                new TypeReference<List<PaymentStatus>>() {});
    }
}
