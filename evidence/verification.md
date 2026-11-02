# Automated Verification

| Date | Runtime | Command | Result |
|---|---|---|---|
| 2026-11-02 | Java 11.0.24 | `mvn test` | PASS: 9 tests, 0 failures (initial suite) |
| 2026-11-02 | Java 11.0.24 | focused mutated test | EXPECTED RED: 1 failure |
| 2026-11-02 | Java 11.0.24 | `mvn -q test` | PASS: 10 tests, exit 0 |
| 2026-11-02 | Java 11.0.24 | `mvn -q package` | PASS: executable jar built, exit 0 |
| 2026-11-02 | Java 11.0.24 | static secret-pattern scan | PASS: no secret-shaped value |
| 2026-11-02 | Java 11.0.24 | OWASP Dependency-Check | INCONCLUSIVE: NVD feed unavailable/stalled |
| 2026-11-02 | Java 11.0.24 | Current `./mvnw -q test` rerun | PASS: 10 tests, 0 failures/errors/skips |
| 2026-11-02 | Java 11.0.24 | Manual QA matrix execution | PASS: all scheduled manual checks matched expected results |
| 2026-11-02 | Java 11.0.24 | OWASP Dependency-Check retry | INCONCLUSIVE: no report after bounded wait; stopped safely |
| 2026-11-02 | Java 11.0.24 | CAP-007 `./mvnw -q clean verify` | PASS: 13 tests, 0 failures/errors/skips |
| 2026-11-02 | Bash | AI gate validator scenarios | PASS: complete PASS accepted; FAIL, incomplete, missing, duplicate, trailing, conflicting and Critical/High-with-PASS reports rejected |

See `evidence/manual-qa-results.md` for the HTTP execution record and `evidence/security-commands.md` for the
exact dependency-gate limitation and safe rerun command.
