# AI Coding Workflow Capstone

Submission-ready Java 11 project implementing the complete workflow from engineering foundation and feature
discovery through delivery assurance and QA handoff.
The working feature is an authenticated Task Tracking REST API.

## Prerequisites

- JDK 11
- Maven 3.8+

## Build and test

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 11) # macOS
./mvnw clean test
./mvnw package
```

## Run

Provide your own local credentials; do not commit them.

The easiest option is the interactive local launcher. It safely prompts for the password instead of saving
it in the repository:

```bash
./run-local.sh
```

Press Enter to use `local-user` as the username, type any non-empty local password, and keep that terminal
open while testing the API.

Alternatively, export the values manually:

```bash
export CAPSTONE_USERNAME='choose-a-local-user'
export CAPSTONE_PASSWORD='choose-a-strong-local-password'
./mvnw spring-boot:run
```

## Try the API

```bash
curl -i -u "$CAPSTONE_USERNAME:$CAPSTONE_PASSWORD" \
  -H 'Content-Type: application/json' \
  -d '{"title":"Prepare demo","description":"Show capstone evidence"}' \
  http://localhost:8080/api/v1/tasks

curl -i -u "$CAPSTONE_USERNAME:$CAPSTONE_PASSWORD" \
  http://localhost:8080/api/v1/tasks

curl -i -u "$CAPSTONE_USERNAME:$CAPSTONE_PASSWORD" \
  -X PATCH -H 'Content-Type: application/json' \
  -d '{"status":"DONE"}' \
  http://localhost:8080/api/v1/tasks/1/status
```

Expected: create returns 201 and `TODO`; list returns the saved task; patch returns 200 and `DONE`.
The in-memory database is cleared when the application stops.

## Submission map

| Requirement | Evidence |
|---|---|
| Engineering foundation, agent and skill | `CLAUDE.md`, `.claude/`, `docs/engineering-foundation/` |
| Feature discovery, prototype, spec and tickets | `docs/feature-discovery/`, `docs/spec/` |
| Working feature and TDD | `src/`, `evidence/tdd-evidence.md` |
| AI review and security audit | `docs/delivery-assurance/ai-code-review.md`, `docs/delivery-assurance/security-audit.md` |
| Human PR review | `docs/delivery-assurance/human-pr-review.md` (reviewer sign-off required) |
| QA and risks | `docs/delivery-assurance/qa-test-matrix.md`, `docs/delivery-assurance/known-risk-log.md` |
| End-to-end mapping | `docs/traceability-matrix.md` |

## Honest gate status

Automated implementation, tests, AI review and static secret review are complete. The NVD dependency scan
was inconclusive and must be rerun with an approved external API key. A real human reviewer must also sign
the PR review record before the full assessment can be claimed.

# ai-coding-capstone