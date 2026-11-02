# Automated AI Code Review Instructions

Review the changes on the current branch against the configured base branch.

Check for:

- correctness and acceptance-criteria regressions;
- authentication, authorization, validation and information exposure;
- exception handling and transaction behavior;
- missing or misleading tests;
- Java 11 and Spring Boot 2.7 compatibility;
- maintainability problems that materially affect this bounded feature.

Return a complete Markdown report. Start with `# Automated AI Code Review Report` and include:

1. review scope and base branch;
2. a severity-ranked findings table using Critical, High, Medium and Low;
3. file paths and line numbers for every actionable finding;
4. recommended remediation for every finding;
5. a gate decision of PASS only when no Critical or High finding is open;
6. a short limitations section explaining that AI review does not replace human PR approval.

Do not modify any project file. Do not include secrets, environment values or credentials in the report.
If no actionable finding exists, say so explicitly rather than inventing one.
