# Security Command Evidence

## Static secret scan - PASS

Date: 2026-11-02

The repository was searched for private-key headers, AWS access-key prefixes and common inline
token/API-key/secret assignments. No secret-shaped value was found. A second targeted search found only:

- environment placeholders for `CAPSTONE_USERNAME` and `CAPSTONE_PASSWORD`;
- clearly non-production test placeholders in `src/test/resources/application.properties`.

No blocked key file or external credential store was read.

## OWASP Dependency-Check - INCONCLUSIVE / BLOCKED

Attempt 1: plugin 9.2.0 exited 1 because the NVD API returned 403/404 before any database existed.  
Attempt 2: plugin 12.2.2 (verified from Maven Central metadata) downloaded/updated data but produced no scan
result after an extended bounded wait; it was stopped with exit 130.

Attempt 3 (2026-11-02): plugin 12.2.2 was retried with JSON output and `failBuildOnCVSS=7`. It produced no
result during a second bounded wait and was stopped. No report was generated. The external feed gate therefore
remains blocked rather than being represented as a pass.

No dependency vulnerability result was produced, so this evidence must not be represented as a PASS.
Before final submission, run the following in an environment with an approved NVD API key supplied outside
the repository:

```bash
mvn org.owasp:dependency-check-maven:12.2.2:check \
  -DnvdApiKeyEnvironmentVariable=NVD_API_KEY \
  -Dformat=JSON -DfailBuildOnCVSS=7
```

Expected gate: exit 0 and no CVSS 7+ finding. Never commit or paste the API key.
