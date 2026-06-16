# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [3.2.0] - Unreleased

Full rewrite of the client against the **Smobilpay partner API v3.2.0**. The
client major version is aligned with the API version it targets, so this
release jumps from the `1.x` line directly to `3.2.0`.

> **Breaking:** HMAC request signing has been removed. Partners that still
> require HMAC must remain on the legacy **1.x** line (git tag `1.1.0`), which
> predates this `org.maviance:smobilpay-java-client` artifact and is unaffected
> by this release.

### Removed

- **HMAC request signing (BREAKING).** The legacy HMAC authentication scheme
  is no longer supported. All requests now authenticate via OAuth 2.0 only.

### Added

- **OAuth 2.0 `client_credentials` authentication.** The client mints, caches,
  and refreshes a bearer token automatically from `POST /oauth/token`.
- **JSON request bodies.** Confirmation and related calls send JSON payloads.
- **Pre-payment account validation** via `POST /v2/validate`.
- **`SmobilpayTimeoutException`.** Request timeouts now surface as a typed
  subtype of `SmobilpayException` (carrying the configured timeout) instead of
  a generic transport error, so callers can handle timeouts distinctly.

### Changed

- **Artifact renamed (BREAKING).** Coordinates are now
  `org.maviance:smobilpay-java-client`.
- Requires **Java 17 or newer** at build and runtime.
- HTTP transport now uses the JDK's built-in `java.net.http.HttpClient`; the
  only third-party runtime dependencies are Jackson Databind and SLF4J.

### Fixed

- **Reactive OAuth 401 refresh.** A `401` on a secured request now forces a
  single token refresh and retries the request once, instead of surfacing
  immediately as a `SmobilpayApiException`. Recovers from server-side token
  expiry, clock drift, and revocation that proactive (clock-based) refresh
  cannot detect. Bounded to one retry (MPAY-30042).

## [1.1.0] - 2025-01-24

- Added `cdata`, `tag`, and `errorCode` fields; dependency fixes.
- Published under legacy coordinates `io.swagger:swagger-java-client` (the
  generated-client artifact name), not `org.maviance:smobilpay-java-client`.

## [1.0.0] - 2021-06-16

- Initial public release (HMAC-authenticated client).
- Published under legacy coordinates `com.maviance.app:s3p-java-client`.

[3.2.0]: https://github.com/maviance/smobilpay-java/releases/tag/3.2.0
[1.1.0]: https://github.com/maviance/smobilpay-java/releases/tag/1.1.0
[1.0.0]: https://github.com/maviance/smobilpay-java/releases/tag/1.0.0
