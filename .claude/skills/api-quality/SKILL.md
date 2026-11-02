---
name: api-quality
description: Verify a Java REST endpoint against the capstone specification, tests and security checklist.
---

# API Quality Check

1. Read `docs/spec/feature-spec.md` and identify acceptance criteria touched by the change.
2. Map each criterion to code and at least one test in `docs/traceability-matrix.md`.
3. Run the focused test, then the full test suite on Java 11.
4. Check authentication, input bounds, generic errors, secret handling and unintended data exposure.
5. Report commands and results. Do not claim a command ran if it did not.
6. Give a PASS/BLOCK result; any open critical/high security finding blocks delivery.
