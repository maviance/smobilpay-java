# s3p-java-client

Java client library for the **Smobilpay S3P partner API** (v3.2.0).

This is the curated, partner-facing client. It covers every endpoint a
partner integrator needs to integrate payment collections, payouts,
value-added services, and account/service discovery — and nothing else.

## What this client does

- **Payment collections.** Take payment from a customer's mobile wallet
  via a quote-then-confirm flow. Works for collections, bill payment,
  top-up, voucher purchase, product purchase, and subscription top-up.
- **Disbursements.** Send funds out to a recipient's mobile wallet.
- **Account and service discovery.** Retrieve the static catalog of
  merchants, services, products, and payment items needed to drive a
  payment UI.
- **Status verification.** Look up the live status of a previously
  issued transaction by `ptn` or by your own custom `trid`, and search
  historical activity by date range.

## Requirements

- **Java 17 or newer** at runtime and at build time.
- Network access to the base URL issued by Maviance support.
- An OAuth 2.0 credential pair (`publicKey` / `secretKey`) issued during
  partner onboarding.

This client uses the JDK's built-in `java.net.http.HttpClient`. The only
third-party dependencies are Jackson Databind (for JSON) and SLF4J (for
logging facade). No HTTP client conflicts with Spring Boot, Quarkus,
Micronaut, or stand-alone Java applications.

## Installation

The library is published as `org.maviance:s3p-java-client:3.2.0`.

### Gradle (Groovy DSL)

```groovy
dependencies {
    implementation 'org.maviance:s3p-java-client:3.2.0'
}
```

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("org.maviance:s3p-java-client:3.2.0")
}
```

### Maven

```xml
<dependency>
    <groupId>org.maviance</groupId>
    <artifactId>s3p-java-client</artifactId>
    <version>3.2.0</version>
</dependency>
```

## Quick start

The library exposes a single `S3pClient` facade. Construct it once per
application with your partner credentials; it lazily mints and caches
the OAuth 2.0 bearer token for the lifetime of the JVM.

```java
import org.maviance.s3p.S3pClient;
import org.maviance.s3p.S3pConfig;
import org.maviance.s3p.model.Ping;

S3pConfig config = S3pConfig.builder()
        .baseUrl("https://api.example.invalid")           // issued during onboarding
        .credentials(
                System.getenv("S3P_PUBLIC_KEY"),
                System.getenv("S3P_SECRET_KEY"))
        .build();

try (S3pClient client = S3pClient.create(config)) {
    Ping pong = client.verify().ping();
    System.out.println("Server time: " + pong.time());
    System.out.println("Server version: " + pong.version());
}
```

## Authentication

The S3P API uses **OAuth 2.0 `client_credentials`** exclusively. HMAC
signing (the legacy `s3pAuth` scheme) is **not** supported.

The client handles token issuance for you:

1. On the first authenticated request, the client POSTs
   `Basic base64(publicKey:secretKey)` to `{baseUrl}/oauth/token`
   with `grant_type=client_credentials`.
2. The returned JWT is cached in memory and attached as
   `Authorization: Bearer <jwt>` on every subsequent request.
3. The token is reused until it is within `tokenRefreshSkew` seconds of
   expiry (default: 30s); then a fresh one is minted automatically.

To force a refresh (e.g. after a 401), call `client.tokens().refresh()`.

## Conventions

- All requests and responses are **JSON**.
- **Monetary amounts** on `QuoteRequest.amount` are integers in the local
  currency of the payment item (no decimals). Other amount fields on
  responses are floats per spec.
- **Currencies** are ISO 4217 codes (e.g. `XAF`, `EUR`).
- **Countries** are ISO 3166-1 alpha-3 codes (e.g. `CMR`).
- **Errors** raised by the API throw `S3pApiException`. Match on
  `error().get().respCode()` for programmatic handling — that is the
  canonical machine identifier per the partner spec.
- The `x-api-version: 3.0.0` header is attached on every secured
  request. Override via `S3pConfig.builder().apiVersion(...)` if you
  need a different protocol shape.

## End-to-end example — cash-in collection

```java
import org.maviance.s3p.S3pClient;
import org.maviance.s3p.S3pConfig;
import org.maviance.s3p.model.*;

import java.util.List;

S3pConfig config = S3pConfig.builder()
        .baseUrl("https://api.example.invalid")
        .credentials(publicKey, secretKey)
        .build();

try (S3pClient client = S3pClient.create(config)) {

    // 1. Discover the cash-in items available for service 999999
    List<Cashin> cashins = client.masterdata().cashins(999999);
    Cashin cashin = cashins.get(0);

    // 2. Request a quote (amounts are integers in local currency)
    QuoteResponse quote = client.initiate().quote(
            new QuoteRequest(500, cashin.payItemId()));

    // 3. Confirm the collection
    CollectionRequest request = CollectionRequest.builder(
                    quote.quoteId(),
                    "237699999999",         // customer phone (E.164, no leading +)
                    "customer@example.com")  // customer email
            .serviceNumber("2371122334455")  // required when service.isReqServiceNumber()
            .trid("ORDER-2026-05-02-0001")   // optional caller-managed reference
            .tag("retail-front-desk")        // optional reporting tag (max 50 chars)
            .build();

    CollectionResponse response = client.confirm().collect(request);
    System.out.println("PTN: " + response.ptn());
    System.out.println("Status: " + response.status()); // PENDING on x-api-version 3.0.0

    // 4. Poll for final status by PTN
    List<PaymentStatus> statuses =
            client.verify().verifyTransaction(response.ptn(), null);
    System.out.println("Final status: " + statuses.get(0).status());
}
```

## End-to-end example — bill payment

```java
List<Bill> bills = client.initiate().bills("CDE", 4321, "METER-001");
Bill bill = bills.get(0);

QuoteResponse quote = client.initiate().quote(
        new QuoteRequest(bill.amountLocalCur().intValue(), bill.payItemId()));

CollectionRequest request = CollectionRequest.builder(
                quote.quoteId(),
                "237699999999",
                "customer@example.com")
        .serviceNumber("METER-001")
        .customerName("Jane Doe")      // required when service.isReqCustomerName()
        .build();

CollectionResponse response = client.confirm().collect(request);
```

## End-to-end example — voucher purchase

For services of type `VOUCHER` the digital code is delivered on
`CollectionResponse.pin()` once the collection succeeds.

```java
List<Product> vouchers = client.masterdata().vouchers(serviceId);
Product voucher = vouchers.get(0);

QuoteResponse quote = client.initiate().quote(
        new QuoteRequest(voucher.amountLocalCur().intValue(), voucher.payItemId()));

CollectionResponse response = client.confirm().collect(
        CollectionRequest.builder(quote.quoteId(), customerPhone, customerEmail)
                .build());

String redemptionPin = response.pin();
```

## Pre-payment verification

For services that report `isVerifiable: true`, you can verify a service
number before quoting:

```java
boolean valid = client.accountValidation()
        .verifyServiceNumber("ENEO", 1234, "01234567");
```

## Historical lookups

Search by **exactly one** of:

```java
client.verify().historyByPtn("PTN-202605020800001");
client.verify().historyByTrid("ORDER-2026-05-02-0001");
client.verify().historyByDateRange(
        LocalDate.parse("2026-05-01"),
        LocalDate.parse("2026-05-31"));
```

Combinations are rejected by the server with an error envelope.

## Error handling

```java
import org.maviance.s3p.S3pApiException;
import org.maviance.s3p.S3pAuthException;

try {
    QuoteResponse quote = client.initiate().quote(request);
} catch (S3pAuthException e) {
    // OAuth 2.0 token issuance failed — bad credentials, etc.
    log.error("Auth failed: status={}, oauthError={}", e.httpStatus(), e.oauthError());
} catch (S3pApiException e) {
    // S3P API returned a non-2xx with the standard Error envelope
    e.error().ifPresent(err ->
        log.error("API error: respCode={}, devMsg={}, link={}",
                err.respCode(), err.devMsg(), err.link()));
    if (e.httpStatus() == 498) {
        // Quote expired — re-quote and retry
    }
}
```

The full S3P error catalog (the `respCode` → meaning mapping) is
delivered to partners during onboarding.

## Configuration reference

| Option              | Default   | Notes                                              |
|---------------------|-----------|----------------------------------------------------|
| `baseUrl`           | required  | Issued during onboarding                           |
| `credentials`       | required  | `publicKey` / `secretKey` pair                     |
| `apiVersion`        | `3.0.0`   | Value sent as `x-api-version` header               |
| `requestTimeout`    | `30s`     | Per-request timeout for both API and token calls   |
| `tokenRefreshSkew`  | `30s`     | Mint a fresh token this far ahead of expiry        |

To use a custom `HttpClient` (proxies, custom SSL, etc.):

```java
HttpClient http = HttpClient.newBuilder()
        .proxy(ProxySelector.of(new InetSocketAddress("proxy", 3128)))
        .build();
S3pClient client = S3pClient.create(config, http);
```

## Onboarding

Base URL, partner credentials (`publicKey` / `secretKey`), callback URL
registration, and the full error catalog are issued by Maviance support
during partner onboarding. They are intentionally not published in the
spec or this README. Contact **support@smobilpay.com**.

## Development

Build:

```bash
./gradlew build
```

Run tests + coverage gate (80% instruction coverage):

```bash
./gradlew check
```

Coverage report: `build/reports/jacoco/test/html/index.html`.

Generate Javadoc HTML (requires a full JDK with the `javadoc` tool —
not just a JRE):

```bash
./gradlew javadoc                       # HTML at build/docs/javadoc
./gradlew build -PenableJavadocJar=true # also bundles the -javadoc.jar artifact
```

Publish to local Maven cache:

```bash
./gradlew publishToMavenLocal
```

## License

Proprietary — Maviance.
