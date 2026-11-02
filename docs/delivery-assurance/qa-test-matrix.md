# QA Test-Case Matrix

Use a fresh application instance unless a case says otherwise. Authentication means valid locally supplied
credentials. Verify both HTTP response and task database state.

| ID | Type | Preconditions | Steps / data | Expected response | Expected DB change | Automated |
|---|---|---|---|---|---|---|
| QA-P01 | Positive | Authenticated | POST title `Prepare demo`, description `Show evidence` | 201, Location, trimmed fields, `TODO` | One task inserted | Yes |
| QA-P02 | Positive | One task | GET `/api/v1/tasks` | 200 array containing task fields | None | Yes |
| QA-P03 | Positive | One task | GET its id | 200 with exact task | None | Yes |
| QA-P04 | Positive | TODO task | PATCH status `IN_PROGRESS`, then `DONE` | Each returns 200/new status | Status updated each time | Partial |
| QA-N01 | Negative | Authenticated | POST blank title | 400 generic validation message | No insert | Yes |
| QA-N02 | Boundary | Authenticated | POST 100-char title | 201 | One insert | Manual |
| QA-N03 | Boundary | Authenticated | POST 101-char title | 400 | No insert | Covered by validation; manual API case |
| QA-N04 | Boundary | Authenticated | POST 501-char description | 400 generic validation message | No insert | Yes |
| QA-N05 | Negative | Authenticated | GET absent id | 404 `Task not found` | None | Yes |
| QA-N06 | Negative | Existing task | PATCH missing status | 400 | Status unchanged | Manual |
| QA-N07 | Negative | Existing task | PATCH status `INVALID` | 400 generic validation message | Status unchanged | Yes |
| QA-N08 | Edge | Authenticated | GET id `0` or `-1` | 400 | None | Manual |
| QA-S01 | Security | No credentials | Call every task endpoint | 401 | No change | GET automated; repeat all manually |
| QA-S02 | Security | Wrong credentials | GET list | 401, no implementation detail | None | Manual |
| QA-S03 | Security | Authenticated | POST HTML/script text as title | 201 JSON-escaped string; no execution | Stored as plain data | Manual |
| QA-S04 | Security | Authenticated | Cause invalid JSON/status | 400 without stack trace/class detail | No change | Yes |
| QA-S05 | Security | App running | Request `/h2-console` | Not exposed/authorized | None | Manual |
| QA-R01 | Regression | Existing tasks | Create → list → get → change status | All contracts remain stable | Correct insert/update only | Yes across suite |
| QA-R02 | Regression | Clean checkout | Run `mvn clean test` on JDK 11 | Build success, all tests pass | Test DB discarded | Yes |

## Handoff exit criteria

- All positive, negative, security and regression cases executed or explicitly scheduled.
- No critical/high defect open.
- Any failed case has an owner, severity and linked ticket.
- Human PR reviewer signs `human-pr-review.md` before merge.

## Execution status

All cases previously marked manual or partial were executed against a locally running application on
2026-11-02 and passed. The complete observed HTTP results are recorded in `evidence/manual-qa-results.md`.
The automated regression suite was rerun in the same handoff and passed all 10 tests.
