# Decision Log

| ID | Decision | Consequence |
|---|---|---|
| ADR-01 | Spring Boot 2.7.18 and Java 11 | Supported combination matching the assignment runtime. |
| ADR-02 | H2 in-memory database | Safe and simple, but data is lost on restart. |
| ADR-03 | HTTP Basic credentials from environment | No committed secret; suitable only for TLS-protected demos. |
| ADR-04 | Explicit DTOs, no Lombok/mapping library | Small dependency surface and visible contract. |
| ADR-05 | OWASP Dependency-Check 12.2.2 | Current Java 11-compatible scan line; database download needs network. |
