# TDD Evidence

## RED - reproduced failing gate

Date: 2026-11-02  
Command: `mvn -q -Dtest=TaskServiceTest#createsTaskWithDefaultTodoStatus test`  
Temporary test mutation: expected `IN_PROGRESS` while the requirement and implementation default to `TODO`.

Observed result:

```text
Tests run: 1, Failures: 1, Errors: 0, Skipped: 0
expected: IN_PROGRESS
 but was: TODO
BUILD FAILURE (exit 1)
```

This is explicitly a reproducible RED-gate demonstration captured during assignment assembly, not a claim
that version-control history proves the original implementation order. The mutation was immediately removed.

## GREEN

Date: 2026-11-02  
Command: `mvn -q test`

Observed result: 10 tests passed with exit code 0 (3 service unit tests and 7 API integration tests).

## REFACTOR

- Kept controller HTTP-only and moved behavior to `TaskService`.
- Centralized entity lookup in `findTask`.
- Centralized safe error bodies in `ApiExceptionHandler`.
- Re-ran the complete suite after refactoring: exit code 0.
