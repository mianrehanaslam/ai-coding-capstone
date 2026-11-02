# Automated AI Code Review Instructions

Review the changes on the current branch against the configured base branch.

Check for:

- correctness and acceptance-criteria regressions;
- authentication, authorization, validation and information exposure;
- exception handling and transaction behavior;
- missing or misleading tests;
- Java 11 and Spring Boot 2.7 compatibility;
- maintainability problems that materially affect this bounded feature.

Return structured findings matching the supplied JSON schema. Include:

1. review scope and base branch;
2. severity-ranked findings using Critical, High, Medium and Low, with OPEN or RESOLVED status;
3. file paths and line numbers for every actionable finding;
4. recommended remediation for every finding;
5. a short limitations section explaining that AI review does not replace human PR approval.

Do not supply a gate decision. The local processor derives PASS or FAIL from the structured finding severities
and statuses.

Do not modify any project file. Do not include secrets, environment values or credentials in the report.
If no actionable finding exists, say so explicitly rather than inventing one.
