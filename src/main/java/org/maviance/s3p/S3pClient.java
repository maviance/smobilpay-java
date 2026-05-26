package org.maviance.s3p;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.maviance.s3p.api.AccountValidationApi;
import org.maviance.s3p.api.ConfirmApi;
import org.maviance.s3p.api.InitiateApi;
import org.maviance.s3p.api.MasterdataApi;
import org.maviance.s3p.api.VerifyApi;
import org.maviance.s3p.auth.OAuth2TokenManager;
import org.maviance.s3p.http.HttpTransport;
import org.maviance.s3p.http.JsonMapper;

import java.net.http.HttpClient;

/**
 * Top-level entry point for the Smobilpay S3P partner API.
 *
 * <p>Construct a client with {@link S3pConfig}; the client lazily mints an
 * OAuth 2.0 bearer token on the first authenticated request and caches it
 * until expiry. Pick an API group via the accessor methods:
 *
 * <pre>{@code
 * S3pConfig config = S3pConfig.builder()
 *     .baseUrl("https://api.example.invalid")
 *     .credentials(System.getenv("S3P_PUBLIC_KEY"), System.getenv("S3P_SECRET_KEY"))
 *     .build();
 *
 * try (S3pClient client = S3pClient.create(config)) {
 *     Ping pong = client.verify().ping();
 *     List<Merchant> merchants = client.masterdata().merchants();
 * }
 * }</pre>
 *
 * <p>{@link S3pClient} is thread-safe and intended to be reused for the
 * lifetime of the application.
 */
public final class S3pClient implements AutoCloseable {

    private final S3pConfig config;
    private final HttpClient httpClient;
    private final OAuth2TokenManager tokenManager;
    private final HttpTransport transport;

    private final MasterdataApi masterdata;
    private final AccountValidationApi accountValidation;
    private final InitiateApi initiate;
    private final ConfirmApi confirm;
    private final VerifyApi verify;

    private S3pClient(S3pConfig config, HttpClient httpClient, ObjectMapper mapper) {
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
    public static S3pClient create(S3pConfig config) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(config.requestTimeout())
                .build();
        return new S3pClient(config, httpClient, JsonMapper.create());
    }

    /** Builds a client with a custom {@link HttpClient} — useful for proxy/SSL customization. */
    public static S3pClient create(S3pConfig config, HttpClient httpClient) {
        return new S3pClient(config, httpClient, JsonMapper.create());
    }

    /** Builds a client with custom transport components. Primarily for tests. */
    public static S3pClient create(S3pConfig config, HttpClient httpClient, ObjectMapper mapper) {
        return new S3pClient(config, httpClient, mapper);
    }

    public S3pConfig config() {
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
