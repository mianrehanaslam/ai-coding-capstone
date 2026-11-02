# Project Guidance

This is a Java 11, Spring Boot 2.7 capstone API. Keep changes small, spec-driven and traceable.

## Workflow

1. Read `docs/spec/feature-spec.md` and the relevant ticket.
2. Add or change a test first and capture the failing result.
3. Implement the smallest passing change, then refactor with tests green.
4. Run `mvn test` with JDK 11.
5. Review against acceptance criteria, then run the security checklist.

## Rules

- Do not add secrets. Runtime credentials come from environment variables.
- Do not add dependencies without recording the reason in `docs/decisions.md`.
- Controllers handle HTTP only; business behavior belongs in services.
- Validate every external input and return generic error messages.
- Never claim human approval until a named reviewer records it.
- Update `docs/traceability-matrix.md` when scope, code or tests change.
