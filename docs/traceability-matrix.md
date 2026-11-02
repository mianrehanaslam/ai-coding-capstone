# Traceability Matrix

| Acceptance criterion | Ticket | Implementation | Automated evidence | QA cases |
|---|---|---|---|---|
| AC-01 valid create/default TODO | CAP-002 | `TaskController.create`, `TaskService.create`, `Task` constructor | `createsTaskWithDefaultTodoStatus`, `createsAndReadsTask` | QA-P01 |
| AC-02 title validation | CAP-002 | `CreateTaskRequest.title` | `rejectsBlankTitle` | QA-N01..N03 |
| AC-03 description max 500 | CAP-002 | `CreateTaskRequest.description` | `rejectsOverlongDescription` | QA-N04 |
| AC-04 list/get/generic 404 | CAP-003 | `findAll`, `findById`, exception handler | `createsAndReadsTask`, `returnsGenericNotFoundResponse`, `reportsMissingTask` | QA-P02, P03, N05 |
| AC-05 valid status persists | CAP-004 | `updateStatus`, `Task.changeStatus` | `updatesExistingTaskStatus`, `changesTaskStatus` | QA-P04 |
| AC-06 invalid/missing status rejected | CAP-004 | request validation and exception handler | `rejectsUnknownStatus` | QA-N06, N07 |
| AC-07 authentication required | CAP-001/002/003/004 | `SecurityConfig` | `rejectsUnauthenticatedRequest` | QA-S01, S02 |
| AC-08 no committed secret/safe errors | CAP-001/005 | environment placeholders, exception handler, error properties | context tests plus security commands | QA-S04, S05 |
| AC-09 optional status filter | CAP-007 | `TaskController.findAll`, `TaskService.findAll`, `TaskRepository.findByStatus` | `filtersTasksByStatus`, `rejectsUnknownStatusFilter` | QA-P05, QA-N09 |

## Ticket completion

| Ticket | Status | Evidence |
|---|---|---|
| CAP-001 | Implemented | Project starts in test context with secured routes and H2 |
| CAP-002 | Implemented | Create behavior and validation tests green |
| CAP-003 | Implemented | Query and missing-task tests green |
| CAP-004 | Implemented | Status tests green |
| CAP-005 | Blocked at external gates | NVD dependency result and human signature outstanding |
| CAP-007 | Implemented | Status-filter unit and integration tests green |
