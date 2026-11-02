# Manual QA Execution Results

**Execution date:** 2026-11-02  
**Runtime:** Java 11.0.24, Spring Boot 2.7.18, local H2 database  
**Environment:** Local application on `http://localhost:8080` with temporary, non-production credentials  
**Result:** PASS - all planned manual checks produced the expected HTTP and database behavior.

Temporary credentials were supplied only as process environment variables. They were not written to project
configuration or retained as submission credentials.

| QA ID | Check executed | Expected | Observed | Result |
|---|---|---|---|---|
| QA-P04 | Change `TODO` to `IN_PROGRESS`, then `DONE` | 200 and persisted status each time | Both responses were 200 with the requested status | PASS |
| QA-N02 | Create with exactly 100 title characters | 201 | 201 | PASS |
| QA-N03 | Create with 101 title characters | 400, no insert | 400 with generic validation response | PASS |
| QA-N04 | Create with 501 description characters | 400, no insert | 400 with generic validation response | PASS |
| QA-N06 | Patch an existing task with missing status | 400, status unchanged | 400; subsequent GET remained `TODO` | PASS |
| QA-N08 | Get task IDs `0` and `-1` | 400 | Both returned 400 with generic validation response | PASS |
| QA-S01 | Call list, get, create and patch without credentials | 401 for every endpoint | All four requests returned 401 | PASS |
| QA-S02 | Call list with an incorrect password | 401 | 401 | PASS |
| QA-S03 | Create a title containing script markup | 201; stored as inert JSON text | 201; returned as a JSON string with no execution | PASS |
| QA-S04 | Inspect invalid title, description, status and identifier responses | No stack trace or Java class detail | Generic `Request validation failed` bodies only | PASS |
| QA-S05 | Request `/h2-console` while authenticated | Console not exposed | 404 | PASS |

## Automated regression executed with the handoff

Command:

```bash
JAVA_HOME=$(/usr/libexec/java_home -v 11) ./mvnw -q test
```

Observed result: 10 tests passed, 0 failures, 0 errors and 0 skipped.

## Test-data disposition

The application was stopped after execution. Because the project uses an in-memory H2 database, all manual
QA records were discarded automatically.
