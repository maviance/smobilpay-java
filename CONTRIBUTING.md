# Contributing

Thank you for considering a contribution to the Smobilpay Java client. This
document describes the workflow we follow for issues, pull requests, and
local development.

## Reporting issues

- Search existing issues before opening a new one.
- For bug reports, include: the client version, JDK version, a minimal
  reproducer, and the observed vs. expected behavior.
- For security issues, **do not** open a public issue — see
  [SECURITY.md](SECURITY.md).

## Development setup

Requirements:

- JDK 17 or newer (full JDK, not just a JRE — Javadoc generation needs the
  `javadoc` tool).
- The bundled Gradle wrapper (`./gradlew`) — do not install Gradle globally.

Useful commands:

```bash
./gradlew build           # compile + test + coverage gate
./gradlew check           # tests + coverage verification (80% instructions)
./gradlew javadoc         # Javadoc at build/docs/javadoc
./gradlew runSmokeTest    # live smoke test against a partner environment
                          # (requires smoke-test.json — see smoke-test.example.json)
```

## Pull requests

Before opening a PR:

1. **One change per PR.** Keep diffs focused — separate refactors from
   functional changes.
2. **All tests pass.** `./gradlew check` must be green locally.
3. **Coverage stays above 80% instructions.** New code should come with
   tests at the same standard as existing code (WireMock-driven HTTP
   tests + Jackson round-trip tests for any new models).
4. **No new public API without docs.** Any new public type or method
   needs a Javadoc paragraph describing what it does, what it returns,
   and when it throws.
5. **Backwards compatibility.** This client follows semver. Breaking
   changes to the public API require a major version bump and a clear
   migration note in the PR description.
6. **Commit messages** follow the conventional-commits style already in
   the history (`feat:`, `fix:`, `refactor:`, `test:`, `docs:`, `chore:`).

## Testing conventions

- Unit + HTTP behavior: JUnit 5 + AssertJ + WireMock.
- Jackson round-trip: round-trip JSON for every new model record — `@JsonCreator`
  parsing **and** serialization back to the wire shape.
- Smoke test: keep `SmokeTest.java` quote-only by default; only opt in to
  the real `POST /v2/collectstd` from the `cashin` block when the operator
  has explicitly set `collect: true` in their config.

## Code style

- Java 17 features are fine (records, pattern matching, text blocks).
- Public API uses records for response models (immutable, structural,
  Jackson-friendly).
- Prefer small, focused classes over large utilities.
- Public Javadoc on every exported type and method.

## License

By contributing, you agree that your contributions will be licensed under
the [Apache License, Version 2.0](LICENSE).
