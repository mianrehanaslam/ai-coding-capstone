# Known-Risk Log

| ID | Risk | Severity | Treatment | Owner / trigger |
|---|---|---|---|---|
| KR-01 | HTTP Basic exposes credentials if used without TLS. | Medium | Localhost only; require TLS and managed identity before remote use. | Owner before deployment |
| KR-02 | Unpaginated list can grow without bound. | Low | Accepted for small in-memory training data; add pagination for durable use. | Owner when persistence changes |
| KR-03 | CSRF disabled could become unsafe if browser cookie authentication is introduced. | Medium | Keep Basic stateless; reassess CSRF with any browser/session design. | Security reviewer on auth change |
| KR-04 | Data is lost on restart. | Low | Intentional H2 training constraint. | Product owner if durability required |
| KR-05 | Dependency findings change over time. | Medium | Rerun OWASP scan before release/deployment. | Build owner per release |
