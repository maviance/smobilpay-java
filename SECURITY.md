# Security Policy

## Reporting a vulnerability

If you believe you have found a security vulnerability in this client
library, please **do not open a public GitHub issue or pull request**.
Public disclosure before a fix is available puts users at risk.

Instead, report the issue through a private channel:

- Use GitHub's [private vulnerability reporting](https://docs.github.com/en/code-security/security-advisories/guidance-on-reporting-and-writing-information-about-vulnerabilities/privately-reporting-a-security-vulnerability)
  feature on this repository if it is enabled, **or**
- Contact the maintainers directly through the channel listed in this
  project's release notes / maintainer contact (set this before public
  release).

When reporting, please include as much detail as you can:

- The version of the library you are using.
- A description of the vulnerability and the impact you believe it has.
- A minimal reproducer (code snippet, request flow, or test case).
- Any suggested mitigations or fixes, if you have them.

We will acknowledge receipt within a reasonable timeframe and keep you
updated on progress toward a fix and coordinated disclosure.

## Scope

In-scope for this policy:

- Vulnerabilities in the client library code itself
  (`src/main/java/org/maviance/smobilpay/**`).
- Vulnerabilities in the build configuration that ship with the
  published artifact.

Out of scope:

- Vulnerabilities in the upstream Smobilpay partner API — report those
  to your partner support channel, not here.
- Vulnerabilities in third-party dependencies — please report those to
  the dependency's own maintainers; we'll track an upgrade once a fix
  is published.
- Issues that require an attacker to already have the partner's
  `secretKey` (the credential itself is the trust boundary).

## Credential handling

This client never logs credentials, never persists them to disk, and
never sends them anywhere except the configured `baseUrl`. If you
observe any leakage, treat it as in-scope and report it through the
channel above.

If you suspect your `publicKey` / `secretKey` pair has been exposed,
contact your partner support channel immediately to rotate the
credentials. Do not file a GitHub issue with the exposed values.

## Supported versions

Only the latest released minor version receives security fixes. We
recommend upgrading to the most recent release when a fix is published.
