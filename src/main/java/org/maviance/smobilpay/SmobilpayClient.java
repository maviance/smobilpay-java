package org.maviance.smobilpay;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.maviance.smobilpay.api.AccountValidationApi;
import org.maviance.smobilpay.api.ConfirmApi;
import org.maviance.smobilpay.api.InitiateApi;
import org.maviance.smobilpay.api.MasterdataApi;
import org.maviance.smobilpay.api.VerifyApi;
import org.maviance.smobilpay.auth.OAuth2TokenManager;
import org.maviance.smobilpay.http.HttpTransport;
import org.maviance.smobilpay.http.JsonMapper;

import java.net.http.HttpClient;

/**
 * Top-level entry point for the Smobilpay partner API.
 *
 * <p>Construct a client with {@link SmobilpayConfig}; the client lazily mints an
 * OAuth 2.0 bearer token on the first authenticated request and caches it
 * until expiry. Pick an API group via the accessor methods:
 *
 * <pre>{@code
 * SmobilpayConfig config = SmobilpayConfig.builder()
 *     .baseUrl("https://api.example.invalid")
 *     .credentials(System.getenv("SMOBILPAY_PUBLIC_KEY"), System.getenv("SMOBILPAY_SECRET_KEY"))
 *     .build();
 *
 * try (SmobilpayClient client = SmobilpayClient.create(config)) {
 *     Ping pong = client.verify().ping();
 *     List<Merchant> merchants = client.masterdata().merchants();
 * }
 * }</pre>
 *
 * <p>{@link SmobilpayClient} is thread-safe and intended to be reused for the
 * lifetime of the application.
 */
public final class SmobilpayClient implements AutoCloseable {

    private final SmobilpayConfig config;
    private final HttpClient httpClient;
    private final OAuth2TokenManager tokenManager;
    private final HttpTransport transport;

    private final MasterdataApi masterdata;
    private final AccountValidationApi accountValidation;
    private final InitiateApi initiate;
    private final ConfirmApi confirm;
    private final VerifyApi verify;

    private SmobilpayClient(SmobilpayConfig config, HttpClient httpClient, ObjectMapper mapper) {
        this.config = config;
        this.httpClient = httpClient;
        this.tokenManager = new OAuth2TokenManager(httpClient, mapper, config);
        this.transport = new HttpTransport(httpClient, mapper, config, tokenManager);
        this.masterdata = new MasterdataApi(transport);
        this.accountValidation = new AccountValidationApi(transport);
        this.initiate = new InitiateApi(transport);
        this.confirm = new ConfirmApi(transport);
        this.verify = new VerifyApi(transport);
    }

    /** Builds a client with default {@link HttpClient} and JSON mapper. */
    public static SmobilpayClient create(SmobilpayConfig config) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(config.requestTimeout())
                .build();
        return new SmobilpayClient(config, httpClient, JsonMapper.create());
    }

    /** Builds a client with a custom {@link HttpClient} — useful for proxy/SSL customization. */
    public static SmobilpayClient create(SmobilpayConfig config, HttpClient httpClient) {
        return new SmobilpayClient(config, httpClient, JsonMapper.create());
    }

    /** Builds a client with custom transport components. Primarily for tests. */
    public static SmobilpayClient create(SmobilpayConfig config, HttpClient httpClient, ObjectMapper mapper) {
        return new SmobilpayClient(config, httpClient, mapper);
    }

    public SmobilpayConfig config() {
        return config;
    }

    public OAuth2TokenManager tokens() {
        return tokenManager;
    }

    public MasterdataApi masterdata() {
        return masterdata;
    }

    public AccountValidationApi accountValidation() {
        return accountValidation;
    }

    public InitiateApi initiate() {
        return initiate;
    }

    public ConfirmApi confirm() {
        return confirm;
    }

    public VerifyApi verify() {
        return verify;
    }

    /**
     * No-op. JDK {@link HttpClient} (Java 17) has no explicit {@code close()}
     * and releases its connection pool via GC. {@code AutoCloseable} is kept
     * on this type so partners can use try-with-resources cleanly; on JDK 21+
     * the underlying client is finalised naturally too.
     */
    @Override
    public void close() {
        // intentionally empty — Java 17 HttpClient is not Closeable
    }
}
