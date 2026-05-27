# smobilpay-java-client

Java client library for the **Smobilpay partner API** (v3.2.0).

This is the curated, partner-facing client. It covers every endpoint a
partner integrator needs to move money in and out, sell value-added
services, and drive a payment UI from the static catalog.

## What this client does

- **Payment collections.** Take payment from a customer's mobile wallet
  via a quote-then-confirm flow. Money flows *out* of the customer's
  wallet against a `Cashout` item. Works for cash-out (generic
  mobile-money collection), bill payment, top-up, voucher purchase,
  product purchase, and subscription top-up.
- **Disbursements.** Send funds out to a recipient's mobile wallet using
  the same quote-then-confirm flow against a `Cashin` item. Money flows
  *into* the recipient's wallet.
- **Account and service discovery.** Retrieve the static catalog of
  merchants, services, products, and payment items needed to drive a
  payment UI.
- **Status verification.** Look up the live status of a previously
  issued transaction by `ptn` or by your own custom `trid`, and search
  historical activity by date range.
- **Pre-payment account validation.** Check that a customer's service
  number is well-formed and accepted by the merchant before quoting.

## Requirements

- **Java 17 or newer** at runtime and at build time.
- Network access to the base URL issued by Maviance support.
- An OAuth 2.0 credential pair (`publicKey` / `secretKey`) issued during
  partner onboarding.

This client uses the JDK's built-in `java.net.http.HttpClient`. The only
third-party dependencies are Jackson Databind (for JSON) and SLF4J (for
the logging facade). No HTTP client conflicts with Spring Boot, Quarkus,
Micronaut, or stand-alone Java applications.

## Installation

The library is published as `org.maviance:smobilpay-java-client:3.2.0`.

### Gradle (Groovy DSL)

```groovy
dependencies {
    implementation 'org.maviance:smobilpay-java-client:3.2.0'
}
```

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("org.maviance:smobilpay-java-client:3.2.0")
}
```

### Maven

```xml
<dependency>
    <groupId>org.maviance</groupId>
    <artifactId>smobilpay-java-client</artifactId>
    <version>3.2.0</version>
</dependency>
```

## Quick start

The library exposes a single `SmobilpayClient` facade. Construct it once
per application with your partner credentials; it lazily mints and
caches the OAuth 2.0 bearer token for the lifetime of the JVM.

```java
import org.maviance.smobilpay.SmobilpayClient;
import org.maviance.smobilpay.SmobilpayConfig;
import org.maviance.smobilpay.model.Ping;

SmobilpayConfig config = SmobilpayConfig.builder()
        .baseUrl("https://api.example.invalid")           // issued during onboarding
        .credentials(
                System.getenv("SMOBILPAY_PUBLIC_KEY"),
                System.getenv("SMOBILPAY_SECRET_KEY"))
        .build();

try (SmobilpayClient client = SmobilpayClient.create(config)) {
    Ping pong = client.verify().ping();
    System.out.println("Server time: " + pong.time());
    System.out.println("Server version: " + pong.version());
}
```

## Authentication

The Smobilpay API uses **OAuth 2.0 `client_credentials`** exclusively.
Legacy HMAC request signing is **not** supported.

The client handles token issuance for you:

1. On the first authenticated request, the client POSTs
   `Basic base64(publicKey:secretKey)` to `{baseUrl}/oauth/token`
   with `grant_type=client_credentials`.
2. The returned JWT is cached in memory and attached as
   `Authorization: Bearer <jwt>` on every subsequent request.
3. The token is reused until it is within `tokenRefreshSkew` seconds of
   expiry (default: 30s); then a fresh one is minted automatically.

To force a refresh (e.g. after a 401), call `client.tokens().refresh()`.

## Choosing the right flow

Every flow follows the same three-step shape — **discover → quote →
confirm** — and confirmation goes through a single endpoint
(`POST /v2/collectstd`) regardless of whether the payment item is a
cash-out (collection), a bill, a top-up, a voucher, a subscription, a
product, or a cash-in (disbursement).

What changes per flow is the masterdata call you use to discover the
right `payItemId`:

| Use case                       | Masterdata call                         | Item type      | Confirm call             | Notes                                                            |
|--------------------------------|-----------------------------------------|----------------|--------------------------|------------------------------------------------------------------|
| Collection (cash-out)          | `masterdata().cashouts(serviceId)`      | `Cashout`      | `confirm().collect(req)` | Generic mobile-money collection. Money flows *out* of customer's wallet.|
| Bill payment                   | `initiate().bills(merchant, …)`         | `Bill`         | `confirm().collect(req)` | Bill is looked up by `serviceNumber`, not from static masterdata.|
| Airtime top-up                 | `masterdata().topups(serviceId)`        | `Topup`        | `confirm().collect(req)` | Recipient phone goes on `customerPhonenumber` or `serviceNumber`.|
| Voucher purchase               | `masterdata().vouchers(serviceId)`      | `Product`      | `confirm().collect(req)` | Code returned on `CollectionResponse.pin()`.                     |
| Product purchase               | `masterdata().products(serviceId)`      | `Product`      | `confirm().collect(req)` | Same shape as voucher but no PIN on the response.                |
| Subscription top-up (pay-TV …) | `initiate().subscriptions(merchant, …)` | `Subscription` | `confirm().collect(req)` | Looked up by `serviceNumber` *or* `customerNumber`.              |
| Disbursement (cash-in)         | `masterdata().cashins(serviceId)`       | `Cashin`       | `confirm().collect(req)` | Payout to recipient. Same `collect()` endpoint, item is a cash-in.|

For every item type the `payItemId` field is what flows into the quote
request. The `service.isReq*` flags on the `Service` masterdata entry
tell you which optional `CollectionRequest` fields (customer name,
service number, customer number, …) become required for that service.

## Conventions

- All requests and responses are **JSON**.
- **Monetary amounts** on `QuoteRequest.amount` are integers in the
  local currency of the payment item (no decimals). Other amount fields
  on responses are floats per spec.
- **Currencies** are ISO 4217 codes (e.g. `XAF`, `EUR`).
- **Countries** are ISO 3166-1 alpha-3 codes (e.g. `CMR`).
- **Phone numbers** are E.164 without the leading `+` (e.g.
  `237699999999`).
- **Errors** raised by the API throw `SmobilpayApiException`. Match on
  `error().get().respCode()` for programmatic handling — that is the
  canonical machine identifier per the partner spec.
- The `x-api-version: 3.0.0` header is attached on every secured
  request. Override via `SmobilpayConfig.builder().apiVersion(...)` if
  you need a different protocol shape.

## Collection — cash-out

A `Cashout` item collects funds *out* of the customer's mobile wallet
into the partner's balance. This is the generic mobile-money collection
flow.

```java
import org.maviance.smobilpay.SmobilpayClient;
import org.maviance.smobilpay.SmobilpayConfig;
import org.maviance.smobilpay.model.*;

import java.util.List;

SmobilpayConfig config = SmobilpayConfig.builder()
        .baseUrl("https://api.example.invalid")
        .credentials(publicKey, secretKey)
        .build();

try (SmobilpayClient client = SmobilpayClient.create(config)) {

    // 1. Discover the cash-out items available for service 999999
    List<Cashout> cashouts = client.masterdata().cashouts(999999L);
    Cashout cashout = cashouts.get(0);

    // 2. Request a quote (amounts are integers in local currency)
    QuoteResponse quote = client.initiate().quote(
            new QuoteRequest(500, cashout.payItemId()));

    // 3. Confirm the collection
    CollectionRequest request = CollectionRequest.builder(
                    quote.quoteId(),
                    "237699999999",          // customer phone (E.164, no leading +)
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

## Collection — bill payment

```java
List<Bill> bills = client.initiate().bills("CDE", 4321L, "METER-001");
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

## Collection — airtime top-up

```java
List<Topup> topups = client.masterdata().topups(serviceId);
Topup topup = topups.get(0);

// FIXED-amount top-ups quote at the catalog price; CUSTOM-amount top-ups
// take any integer in the local currency.
int amount = topup.amountLocalCur() != null
        ? topup.amountLocalCur().intValue()
        : 500;

QuoteResponse quote = client.initiate().quote(
        new QuoteRequest(amount, topup.payItemId()));

CollectionRequest request = CollectionRequest.builder(
                quote.quoteId(),
                "237699999999",
                "customer@example.com")
        .serviceNumber("237699999999")   // recipient MSISDN
        .build();

CollectionResponse response = client.confirm().collect(request);
```

## Collection — voucher purchase

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

## Collection — product purchase

Generic products work like vouchers but do not return a redemption PIN:

```java
List<Product> products = client.masterdata().products(serviceId);
Product product = products.get(0);

QuoteResponse quote = client.initiate().quote(
        new QuoteRequest(product.amountLocalCur().intValue(), product.payItemId()));

CollectionResponse response = client.confirm().collect(
        CollectionRequest.builder(quote.quoteId(), customerPhone, customerEmail)
                .build());
```

## Collection — subscription top-up

Subscriptions (e.g. pay-TV like Canal+) are looked up by *either*
`serviceNumber` *or* `customerNumber` — pass one and leave the other
`null`. The returned list may contain several `Subscription` items
representing different renewal options for the same customer; pick one
and quote against its `payItemId`.

```java
List<Subscription> subs = client.initiate().subscriptions(
        "CANALPLUS",
        4321L,
        "DECODER-001234",   // serviceNumber
        null);              // customerNumber (or vice versa)
Subscription sub = subs.get(0);

QuoteResponse quote = client.initiate().quote(
        new QuoteRequest(sub.amountLocalCur().intValue(), sub.payItemId()));

CollectionRequest request = CollectionRequest.builder(
                quote.quoteId(),
                "237699999999",
                "customer@example.com")
        .serviceNumber("DECODER-001234")
        .customerName(sub.customerName())
        .build();

CollectionResponse response = client.confirm().collect(request);
```

## Disbursement — cash-in

A `Cashin` item pays funds *into* a recipient's mobile wallet from the
partner's balance. It goes through the same `/v2/collectstd` endpoint
as collections — same `CollectionRequest`, same `CollectionResponse`.

```java
List<Cashin> cashins = client.masterdata().cashins(serviceId);
Cashin cashin = cashins.get(0);

QuoteResponse quote = client.initiate().quote(
        new QuoteRequest(10_000, cashin.payItemId()));

CollectionRequest request = CollectionRequest.builder(
                quote.quoteId(),
                "237699999999",          // recipient phone
                "recipient@example.com")
        .serviceNumber("237699999999")   // recipient MSISDN
        .trid("PAYOUT-2026-05-02-0001")
        .build();

CollectionResponse response = client.confirm().collect(request);
```

## Pre-payment verification

For services that report `isVerifiable: true`, you can verify a service
number before quoting:

```java
boolean valid = client.accountValidation()
        .verifyServiceNumber("ENEO", 1234L, "01234567");
```

## Catalog discovery

Most integrations cache the catalog and refresh it on a schedule:

```java
List<Merchant> merchants = client.masterdata().merchants();
List<Service>  services  = client.masterdata().services();
```

The `Service` record tells you which flow applies (cash-out, bill,
top-up, voucher, product, subscription, cash-in) via its `serviceType`
and which optional `CollectionRequest` fields the merchant requires via
the `isReq*` boolean flags.

## Account and ping utilities

```java
// Liveness check + protocol/version handshake.
Ping pong = client.verify().ping();

// Aggregator-level account info: balance, currency, status.
Account account = client.verify().account();
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
import org.maviance.smobilpay.SmobilpayApiException;
import org.maviance.smobilpay.SmobilpayAuthException;

try {
    QuoteResponse quote = client.initiate().quote(request);
} catch (SmobilpayAuthException e) {
    // OAuth 2.0 token issuance failed — bad credentials, etc.
    log.error("Auth failed: status={}, oauthError={}", e.httpStatus(), e.oauthError());
} catch (SmobilpayApiException e) {
    // API returned a non-2xx with the standard Error envelope
    e.error().ifPresent(err ->
        log.error("API error: respCode={}, devMsg={}, link={}",
                err.respCode(), err.devMsg(), err.link()));
    if (e.httpStatus() == 498) {
        // Quote expired — re-quote and retry
    }
}
```

The full Smobilpay error catalog (the `respCode` → meaning mapping) is
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
SmobilpayClient client = SmobilpayClient.create(config, http);
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
