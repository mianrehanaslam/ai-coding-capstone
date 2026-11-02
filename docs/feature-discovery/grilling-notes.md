# Grilling Notes

## Initial request

Build a deliberately small API feature that demonstrates the complete spec-to-QA workflow.

## Questions and resolved answers

| Question | Decision | Reason |
|---|---|---|
| Who uses it? | One authenticated training user. | Keeps authorization bounded. |
| What is a task? | Title, optional description, status and creation time. | Minimum useful domain. |
| Required operations? | Create, list, read one and change status. | Demonstrates vertical slices without CRUD sprawl. |
| Valid status values? | `TODO`, `IN_PROGRESS`, `DONE`. | Explicit finite workflow. |
| Can a status move backward? | Yes. | No business rule requested; avoids invented scope. |
| Persistence? | In-memory H2 for the exercise. | Safe, runnable and disposable. |
| Authentication? | HTTP Basic with credentials supplied by environment. | Simple security boundary; no stored secret. |
| Error detail? | Generic client-safe messages. | Avoids leaking implementation details. |
| Pagination? | Out of scope. | Dataset is explicitly a training dataset. |
| Deletion/editing? | Out of scope. | Not needed for the stated user outcome. |
| Deployment? | Out of scope. | Training repository only. |

## Assumptions surfaced

- This is a single-instance demonstration; in-memory data loss on restart is accepted.
- A human reviewer must sign the PR record; AI output cannot substitute for that approval.
- The assignment needs evidence artifacts, but claims must remain reproducible rather than fabricated.

## Open questions

None blocking implementation. Production identity, durable persistence and pagination are future decisions.
