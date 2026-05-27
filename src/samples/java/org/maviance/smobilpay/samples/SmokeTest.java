package org.maviance.smobilpay.samples;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.maviance.smobilpay.SmobilpayApiException;
import org.maviance.smobilpay.SmobilpayAuthException;
import org.maviance.smobilpay.SmobilpayClient;
import org.maviance.smobilpay.SmobilpayConfig;
import org.maviance.smobilpay.model.Account;
import org.maviance.smobilpay.model.ApiError;
import org.maviance.smobilpay.model.Bill;
import org.maviance.smobilpay.model.Cashin;
import org.maviance.smobilpay.model.Cashout;
import org.maviance.smobilpay.model.CustomerAccount;
import org.maviance.smobilpay.model.Merchant;
import org.maviance.smobilpay.model.PaymentItem;
import org.maviance.smobilpay.model.PaymentStatus;
import org.maviance.smobilpay.model.Ping;
import org.maviance.smobilpay.model.Product;
import org.maviance.smobilpay.model.QuoteRequest;
import org.maviance.smobilpay.model.QuoteResponse;
import org.maviance.smobilpay.model.Service;
import org.maviance.smobilpay.model.ServiceType;
import org.maviance.smobilpay.model.Subscription;
import org.maviance.smobilpay.model.Topup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Smoke-test harness for the Smobilpay client against a real partner environment.
 *
 * <h2>Configuration</h2>
 *
 * <p>All settings are read from a single JSON config file. The path is
 * resolved in this order:
 *
 * <ol>
 *   <li>The first command-line argument, if present.</li>
 *   <li>The {@code SMOBILPAY_SMOKE_CONFIG} environment variable, if set.</li>
 *   <li>{@code ./smoke-test.json} in the current working directory.</li>
 * </ol>
 *
 * <p>See {@code smoke-test.example.json} at the repo root for a fully
 * populated template. {@code baseUrl}, {@code publicKey} and
 * {@code secretKey} are required; every per-flow block is optional and
 * an absent block simply skips the corresponding scenario.
 *
 * <h2>Spec terminology</h2>
 *
 * <ul>
 *   <li><strong>cashout</strong> = collection (money flows <em>out</em>
 *       of customer's wallet, partner accepts payment).</li>
 *   <li><strong>cashin</strong>  = disbursement (money flows <em>into</em>
 *       recipient's wallet, partner pays out).</li>
 * </ul>
 *
 * <h2>What it does</h2>
 *
 * <p>The harness is intentionally <strong>read-only / quote-only</strong>. It
 * never calls {@code /v2/collectstd}, so it does not move money.
 *
 * <ol>
 *   <li>Ping — proves OAuth 2.0 mint + bearer + {@code x-api-version} work end to end.</li>
 *   <li>Token refresh — forces a fresh mint, re-pings.</li>
 *   <li>Account profile — agent identity, balance, daily limit.</li>
 *   <li>Merchant list — discovery of merchants supported by the system.</li>
 *   <li>Service list — discovery and type distribution.</li>
 *   <li>Per-flow discovery + quote for every configured service type
 *       (cashout, bill, topup, voucher, product, subscription, cashin).</li>
 *   <li>Pre-payment {@code serviceNumber} verification (optional).</li>
 *   <li>History over the last 7 days.</li>
 * </ol>
 *
 * <h2>How to run</h2>
 * <pre>{@code
 * cp smoke-test.example.json smoke-test.json
 * # edit smoke-test.json to fill in baseUrl, publicKey, secretKey, and the
 * # per-flow blocks you want exercised
 * ./gradlew runSmokeTest --console=plain
 * # or with an explicit path:
 * ./gradlew runSmokeTest --console=plain --args="path/to/my-config.json"
 * }</pre>
 *
 * <p>Exit code is 0 when every non-skipped scenario passes, 1 on any failure,
 * 2 on configuration errors before the client could start.
 */
public final class SmokeTest {

    private static final String SEP = "----------------------------------------------------------------------";

    private final SmokeTestConfig cfg;

    private int passed;
    private int failed;
    private int skipped;

    private SmokeTest(SmokeTestConfig cfg) {
        this.cfg = cfg;
    }

    public static void main(String[] args) {
        SmokeTestConfig cfg;
        try {
            cfg = loadConfig(args);
            validateRequiredFields(cfg);
        } catch (ConfigException e) {
            System.err.println("Configuration error: " + e.getMessage());
            System.exit(2);
            return;
        }
        int exit = new SmokeTest(cfg).execute();
        System.exit(exit);
    }

    private int execute() {
        SmobilpayConfig clientConfig = buildClientConfig();

        banner("Smobilpay smoke test  —  baseUrl=" + redact(clientConfig.baseUrl().toString())
                + ", apiVersion=" + clientConfig.apiVersion()
                + ", publicKey=" + redactKey(clientConfig.publicKey()));

        try (SmobilpayClient client = SmobilpayClient.create(clientConfig)) {
            scenarioPing(client);
            scenarioTokenRefresh(client);
            scenarioAccount(client);
            scenarioMerchants(client);
            scenarioServices(client);
            scenarioCashout(client);        // collection
            scenarioBill(client);
            scenarioTopup(client);
            scenarioVoucher(client);
            scenarioProduct(client);
            scenarioSubscription(client);
            scenarioCashin(client);         // disbursement
            scenarioVerifyServiceNumber(client);
            scenarioValidateAccount(client);
            scenarioHistoryLast7Days(client);
        }

        printSummary();
        return failed == 0 ? 0 : 1;
    }

    // --- Scenarios --------------------------------------------------------

    private void scenarioPing(SmobilpayClient client) {
        run("Ping (auth probe)", () -> {
            Ping pong = client.verify().ping();
            require(pong != null && pong.version() != null, "empty response");
            detail("server time:    " + pong.time());
            detail("server version: " + pong.version());
            detail("nonce echo:     " + pong.nonce());
            detail("public key:     " + pong.key());
        });
    }

    private void scenarioTokenRefresh(SmobilpayClient client) {
        run("OAuth 2.0 token refresh", () -> {
            String first = client.tokens().accessToken();
            String forced = client.tokens().refresh();
            require(forced != null && !forced.isEmpty(), "refresh returned empty token");
            Ping pong = client.verify().ping();
            require(pong != null, "ping after refresh returned null");
            detail("first  bearer prefix: " + first.substring(0, Math.min(12, first.length())) + "...");
            detail("forced bearer prefix: " + forced.substring(0, Math.min(12, forced.length())) + "...");
            detail("identical: " + first.equals(forced));
        });
    }

    private void scenarioAccount(SmobilpayClient client) {
        run("Account profile", () -> {
            Account account = client.verify().account();
            require(account != null, "empty response");
            detail("agent:           " + account.agentName() + " (id=" + account.agentId() + ")");
            detail("company:         " + account.companyName());
            detail("balance:         " + account.balance() + " " + account.currency());
            detail("daily limit max: " + account.limitMax());
            detail("limit remaining: " + account.limitRemaining());
        });
    }

    private void scenarioMerchants(SmobilpayClient client) {
        run("Merchant catalog", () -> {
            List<Merchant> merchants = client.masterdata().merchants();
            require(merchants != null, "null response");
            detail("merchants: " + merchants.size());
            int sample = Math.min(5, merchants.size());
            for (int i = 0; i < sample; i++) {
                Merchant m = merchants.get(i);
                detail("  - " + m.merchant() + " : " + m.name() + " (" + m.country() + ", " + m.status() + ")");
            }
            if (merchants.size() > sample) {
                detail("  ...and " + (merchants.size() - sample) + " more");
            }
        });
    }

    private void scenarioServices(SmobilpayClient client) {
        run("Service catalog", () -> {
            List<Service> services = client.masterdata().services();
            require(services != null, "null response");
            detail("services: " + services.size());

            Map<ServiceType, Integer> byType = new TreeMap<>();
            for (Service s : services) {
                byType.merge(s.type(), 1, Integer::sum);
            }
            detail("distribution by type:");
            byType.forEach((t, c) -> detail("  - " + t + ": " + c));

            // Hints for finding hard-to-source serviceIds in the config file:
            // voucher, subscription, and verifiable services are rare in the
            // acceptance catalog and not always listed on the wiki test-data page.
            listServicesOfType(services, ServiceType.VOUCHER, "VOUCHER services");
            listServicesOfType(services, ServiceType.SUBSCRIPTION, "SUBSCRIPTION services");
            listVerifiableServices(services);
        });
    }

    private void listServicesOfType(List<Service> services, ServiceType type, String label) {
        List<Service> matches = services.stream().filter(s -> s.type() == type).toList();
        if (matches.isEmpty()) {
            return;
        }
        detail(label + ":");
        for (Service s : matches) {
            detail(String.format("  - serviceId=%d merchant=%s title=%s",
                    s.serviceid(), s.merchant(), s.title()));
        }
    }

    private void listVerifiableServices(List<Service> services) {
        List<Service> matches = services.stream().filter(Service::isVerifiable).toList();
        if (matches.isEmpty()) {
            return;
        }
        detail("verifiable services (isVerifiable=true) — candidates for the 'verify' block:");
        for (Service s : matches) {
            detail(String.format("  - serviceId=%d merchant=%s title=%s",
                    s.serviceid(), s.merchant(), s.title()));
        }
    }

    private void scenarioCashout(SmobilpayClient client) {
        run("Collection — cash-out (discover + quote)", () -> {
            SmokeTestConfig.CashoutCfg c = cfg.cashout();
            if (c == null) {
                skip("no 'cashout' block in config");
            }
            List<Cashout> items = client.masterdata().cashouts(c.serviceId());
            require(items != null && !items.isEmpty(), "no cashout items for serviceId=" + c.serviceId());
            Cashout item = items.get(0);
            detail("picked: " + item.payItemId() + " (" + item.name()
                    + ", " + item.amountType() + ", local=" + item.amountLocalCur() + " " + item.localCur() + ")");
            quoteAndReport(client, item, c.amount());
        });
    }

    private void scenarioBill(SmobilpayClient client) {
        run("Collection — bill payment (discover + quote)", () -> {
            SmokeTestConfig.BillCfg c = cfg.bill();
            if (c == null) {
                skip("no 'bill' block in config");
            }
            List<Bill> bills = client.initiate().bills(c.merchant(), c.serviceId(), c.serviceNumber());
            require(bills != null && !bills.isEmpty(),
                    "no bills for " + c.merchant() + "/" + c.serviceId() + "/" + c.serviceNumber());
            Bill bill = bills.get(0);
            detail("picked: " + bill.payItemId() + " (" + bill.billType()
                    + ", amount=" + bill.amountLocalCur() + " " + bill.localCur()
                    + ", due=" + bill.billDueDate() + ")");
            quoteAndReport(client, bill, bill.amountLocalCur().intValue());
        });
    }

    private void scenarioTopup(SmobilpayClient client) {
        run("Collection — airtime top-up (discover + quote)", () -> {
            SmokeTestConfig.TopupCfg c = cfg.topup();
            if (c == null) {
                skip("no 'topup' block in config");
            }
            List<Topup> items = client.masterdata().topups(c.serviceId());
            require(items != null && !items.isEmpty(), "no topup items for serviceId=" + c.serviceId());
            Topup item = items.get(0);
            detail("picked: " + item.payItemId() + " (" + item.name()
                    + ", " + item.amountType() + ", local=" + item.amountLocalCur() + " " + item.localCur() + ")");
            quoteAndReport(client, item, c.amount());
        });
    }

    private void scenarioVoucher(SmobilpayClient client) {
        run("Collection — voucher purchase (discover + quote)", () -> {
            SmokeTestConfig.VoucherCfg c = cfg.voucher();
            if (c == null) {
                skip("no 'voucher' block in config");
            }
            List<Product> items;
            try {
                items = client.masterdata().vouchers(c.serviceId());
            } catch (SmobilpayApiException e) {
                if (e.error().map(err -> err.respCode() == 41004).orElse(false)) {
                    skip("/v2/voucher rejects serviceId=" + c.serviceId()
                            + " (respCode 41004) even though the catalog labels it VOUCHER");
                }
                throw e;
            }
            require(items != null && !items.isEmpty(), "no vouchers for serviceId=" + c.serviceId());
            Product item = items.get(0);
            detail("picked: " + item.payItemId() + " (" + item.name()
                    + ", " + item.amountType() + ", local=" + item.amountLocalCur() + " " + item.localCur() + ")");
            quoteAndReport(client, item, resolveAmount(item, c.amount()));
        });
    }

    private void scenarioProduct(SmobilpayClient client) {
        run("Collection — product purchase (discover + quote)", () -> {
            SmokeTestConfig.ProductCfg c = cfg.product();
            if (c == null) {
                skip("no 'product' block in config");
            }
            List<Product> items = client.masterdata().products(c.serviceId());
            require(items != null && !items.isEmpty(), "no products for serviceId=" + c.serviceId());
            Product item = items.get(0);
            detail("picked: " + item.payItemId() + " (" + item.name()
                    + ", " + item.amountType() + ", local=" + item.amountLocalCur() + " " + item.localCur() + ")");
            quoteAndReport(client, item, resolveAmount(item, c.amount()));
        });
    }

    private void scenarioSubscription(SmobilpayClient client) {
        run("Collection — subscription top-up (discover + quote)", () -> {
            SmokeTestConfig.SubscriptionCfg c = cfg.subscription();
            if (c == null) {
                skip("no 'subscription' block in config");
            }
            if (c.serviceNumber() == null && c.customerNumber() == null) {
                skip("subscription block needs either 'serviceNumber' or 'customerNumber'");
            }
            List<Subscription> subs = client.initiate().subscriptions(
                    c.merchant(), c.serviceId(), c.serviceNumber(), c.customerNumber());
            require(subs != null && !subs.isEmpty(),
                    "no subscriptions for " + c.merchant() + "/" + c.serviceId()
                            + " (serviceNumber=" + c.serviceNumber() + ", customerNumber=" + c.customerNumber() + ")");
            Subscription sub = subs.get(0);
            detail("picked: " + sub.payItemId() + " (" + sub.name()
                    + ", customer=" + sub.customerName()
                    + ", amount=" + sub.amountLocalCur() + " " + sub.localCur()
                    + ", due=" + sub.dueDate() + ")");
            quoteAndReport(client, sub, resolveAmount(sub, c.amount()));
        });
    }

    private void scenarioCashin(SmobilpayClient client) {
        run("Disbursement — cash-in (discover + quote)", () -> {
            SmokeTestConfig.CashinCfg c = cfg.cashin();
            if (c == null) {
                skip("no 'cashin' block in config");
            }
            List<Cashin> items = client.masterdata().cashins(c.serviceId());
            require(items != null && !items.isEmpty(), "no cashin items for serviceId=" + c.serviceId());
            Cashin item = items.get(0);
            detail("picked: " + item.payItemId() + " (" + item.name()
                    + ", " + item.amountType() + ", local=" + item.amountLocalCur() + " " + item.localCur() + ")");
            quoteAndReport(client, item, c.amount());
        });
    }

    private void scenarioVerifyServiceNumber(SmobilpayClient client) {
        run("Account validation — verify serviceNumber", () -> {
            SmokeTestConfig.VerifyCfg c = cfg.verify();
            if (c == null) {
                skip("no 'verify' block in config");
            }
            try {
                boolean valid = client.accountValidation()
                        .verifyServiceNumber(c.merchant(), c.serviceId(), c.serviceNumber());
                detail(c.serviceNumber() + " for " + c.merchant() + "/" + c.serviceId()
                        + " -> " + (valid ? "valid" : "invalid"));
            } catch (SmobilpayApiException e) {
                if (e.error().map(err -> err.respCode() == 40408).orElse(false)) {
                    skip("service " + c.merchant() + "/" + c.serviceId()
                            + " does not support pre-payment verification (respCode 40408)");
                }
                throw e;
            }
        });
    }

    private void scenarioValidateAccount(SmobilpayClient client) {
        run("Account validation — validate destination", () -> {
            SmokeTestConfig.ValidateCfg c = cfg.validate();
            if (c == null) {
                skip("no 'validate' block in config");
            }
            try {
                CustomerAccount account = client.accountValidation()
                        .validateAccount(c.destination(), c.serviceId());
                require(account != null, "empty response");
                detail("destination: " + account.destination());
                detail("status:      " + account.status());
                detail("name:        " + account.name());
            } catch (SmobilpayApiException e) {
                if (e.httpStatus() == 401) {
                    skip("GET /v2/validate is a restricted endpoint and is not enabled"
                            + " for this partner (HTTP 401). Compliance review is required —"
                            + " contact your Maviance integration manager.");
                }
                throw e;
            }
        });
    }

    private void scenarioHistoryLast7Days(SmobilpayClient client) {
        run("History - last 7 days", () -> {
            LocalDate today = LocalDate.now();
            LocalDate weekAgo = today.minusDays(7);
            List<PaymentStatus> rows = client.verify().historyByDateRange(weekAgo, today);
            require(rows != null, "null response");
            detail("range:        " + weekAgo + " -> " + today);
            detail("transactions: " + rows.size());
            int sample = Math.min(3, rows.size());
            for (int i = 0; i < sample; i++) {
                PaymentStatus s = rows.get(i);
                detail("  - " + s.ptn() + " : " + s.status()
                        + ", " + s.priceLocalCur() + " " + s.localCur()
                        + ", trid=" + s.trid());
            }
        });
    }

    // --- Helpers ----------------------------------------------------------

    private void quoteAndReport(SmobilpayClient client, PaymentItem item, int amount) {
        QuoteResponse quote = client.initiate().quote(new QuoteRequest(amount, item.payItemId()));
        require(quote != null && quote.quoteId() != null, "empty quote");
        detail("quoteId:        " + quote.quoteId());
        detail("expiresAt:      " + quote.expiresAt());
        detail("price (local):  " + quote.priceLocalCur() + " " + quote.localCur());
        detail("price (system): " + quote.priceSystemCur() + " " + quote.systemCur());
        detail("promotion:      " + quote.promotion());
        detail("(intentionally NOT calling /v2/collectstd)");
    }

    /**
     * Choose a quote amount for items where the catalog price may or may
     * not be set. FIXED-amount items have {@code amountLocalCur} populated
     * and the catalog wins; CUSTOM-amount items (typical for vouchers,
     * top-ups, prepaid subscriptions) need an explicit override in the
     * config block.
     */
    private static int resolveAmount(PaymentItem item, Integer configAmount) {
        if (configAmount != null && configAmount > 0) {
            return configAmount;
        }
        Float local = item.amountLocalCur();
        if (local != null && local >= 1f) {
            return local.intValue();
        }
        throw new IllegalStateException("item " + item.payItemId()
                + " has no fixed catalog amount (got " + local
                + "). Set \"amount\" in this block of smoke-test.json.");
    }

    // --- Harness mechanics ------------------------------------------------

    private interface Scenario {
        void run();
    }

    private void run(String name, Scenario scenario) {
        System.out.println(SEP);
        System.out.println("RUN  " + name);
        try {
            scenario.run();
            passed++;
            System.out.println("PASS " + name);
        } catch (SkipException e) {
            skipped++;
            System.out.println("SKIP " + name + " - " + e.getMessage());
        } catch (SmobilpayAuthException e) {
            failed++;
            System.out.println("FAIL " + name + " - auth error (HTTP " + e.httpStatus()
                    + (e.oauthError() != null ? ", error=" + e.oauthError() : "") + "): " + e.getMessage());
        } catch (SmobilpayApiException e) {
            failed++;
            System.out.println("FAIL " + name + " - API error (HTTP " + e.httpStatus() + ")");
            e.error().ifPresent(this::printApiError);
        } catch (RuntimeException e) {
            failed++;
            System.out.println("FAIL " + name + " - " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private void printApiError(ApiError err) {
        detail("respCode: " + err.respCode());
        detail("devMsg:   " + err.devMsg());
        if (err.usrMsg() != null) {
            detail("usrMsg:   " + err.usrMsg());
        }
        if (err.link() != null) {
            detail("link:     " + err.link());
        }
    }

    private void detail(String line) {
        System.out.println("     " + line);
    }

    private void banner(String message) {
        System.out.println();
        System.out.println("======================================================================");
        System.out.println(message);
        System.out.println("======================================================================");
    }

    private void printSummary() {
        System.out.println(SEP);
        System.out.println(String.format("Summary: %d passed, %d skipped, %d failed", passed, skipped, failed));
        System.out.println(SEP);
    }

    private static void require(boolean ok, String message) {
        if (!ok) {
            throw new IllegalStateException(message);
        }
    }

    private static void skip(String reason) {
        throw new SkipException(reason);
    }

    // --- Config loading ---------------------------------------------------

    private static SmokeTestConfig loadConfig(String[] args) {
        Path path = resolveConfigPath(args);
        if (!Files.isRegularFile(path)) {
            throw new ConfigException("config file not found at " + path.toAbsolutePath()
                    + ". Pass a path as the first argument, set SMOBILPAY_SMOKE_CONFIG,"
                    + " or create ./smoke-test.json (see smoke-test.example.json).");
        }
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(path.toFile(), SmokeTestConfig.class);
        } catch (IOException e) {
            throw new ConfigException("could not parse " + path.toAbsolutePath() + ": " + e.getMessage());
        }
    }

    private static Path resolveConfigPath(String[] args) {
        if (args != null && args.length > 0 && !args[0].isBlank()) {
            return Paths.get(args[0].trim());
        }
        String envPath = System.getenv("SMOBILPAY_SMOKE_CONFIG");
        if (envPath != null && !envPath.isBlank()) {
            return Paths.get(envPath.trim());
        }
        return Paths.get("smoke-test.json");
    }

    private static void validateRequiredFields(SmokeTestConfig cfg) {
        if (cfg == null) {
            throw new ConfigException("config is empty");
        }
        if (cfg.baseUrl() == null || cfg.baseUrl().isBlank()) {
            throw new ConfigException("missing required field 'baseUrl'");
        }
        if (cfg.publicKey() == null || cfg.publicKey().isBlank()) {
            throw new ConfigException("missing required field 'publicKey'");
        }
        if (cfg.secretKey() == null || cfg.secretKey().isBlank()) {
            throw new ConfigException("missing required field 'secretKey'");
        }
    }

    private SmobilpayConfig buildClientConfig() {
        SmobilpayConfig.Builder b = SmobilpayConfig.builder()
                .baseUrl(cfg.baseUrl())
                .credentials(cfg.publicKey(), cfg.secretKey());
        if (cfg.apiVersion() != null && !cfg.apiVersion().isBlank()) {
            b.apiVersion(cfg.apiVersion());
        }
        return b.build();
    }

    private static String redact(String s) {
        return s.endsWith("/") ? s.substring(0, s.length() - 1) : s;
    }

    private static String redactKey(String key) {
        if (key == null || key.length() <= 4) {
            return "****";
        }
        return key.substring(0, 4) + "..." + key.substring(key.length() - 2);
    }

    private static final class SkipException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        SkipException(String message) {
            super(message);
        }
    }

    private static final class ConfigException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        ConfigException(String message) {
            super(message);
        }
    }
}
