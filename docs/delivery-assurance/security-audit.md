# Severity-Ranked Security Audit

**Scope:** authentication, authorization boundary, validation, error exposure, secrets and dependencies  
**Date:** 2026-11-02

## Findings

| ID | Severity | Status | Evidence / action |
|---|---|---|---|
| SEC-01 | Critical | None found | No unsafe deserialization, command execution or exposed secret path found. |
| SEC-02 | High | None open | Every `/api/v1/tasks/**` request requires authentication; unauthenticated test returns 401. |
| SEC-03 | Medium | Mitigated | Basic credentials require TLS in any remote environment; this project is local-only. See KR-01. |
| SEC-04 | Medium | Accepted | CSRF is disabled for the API. Safe only while it remains a non-browser, stateless API; see KR-03. |
| SEC-05 | Low | Closed | H2 console explicitly disabled. |
| SEC-06 | Low | Closed | Title/description/id/status inputs are bounded and validated. |
| SEC-07 | Low | Closed | Error messages are generic; stack traces and internal messages are disabled. |

## Secret scan

Search scope: source, config and docs. Patterns: private-key headers, common API-token assignments, AWS key
prefixes and committed runtime credential values. Result is recorded in `evidence/security-commands.md`.

## Dependency scan

The OWASP Dependency-Check command and outcome are recorded in `evidence/security-commands.md`. The NVD-backed
scan was inconclusive, so dependency risk is unverified and remains a blocking gate.

## Gate

**BLOCKED / INCONCLUSIVE** - static application review found no critical/high issue, but the dependency scan
did not complete. Obtain an approved external NVD API key and produce a clean report before claiming the
security gate. Remote deployment also requires TLS and a production identity design.
