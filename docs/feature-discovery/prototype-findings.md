# Disposable Prototype Findings

## Question tested

Can Spring MVC bind a status enum and return a small, predictable JSON contract without custom parsing?

## Prototype

A throwaway controller/service sketch used `TaskStatus` directly in `UpdateTaskStatusRequest`, with MockMvc
requests for `DONE` and `INVALID`.

## Findings

- Valid enum values bind cleanly and serialize as stable uppercase strings.
- Unknown enum values produce HTTP 400 before the service executes.
- Bean Validation handles blank titles and missing status consistently.
- A controller-service-repository split is enough; a mapping framework would add no value.

## Decision

Use the enum directly, keep request/response DTOs explicit and cover invalid binding at the API boundary.
The prototype itself was disposable; its confirmed behavior became automated integration tests.
