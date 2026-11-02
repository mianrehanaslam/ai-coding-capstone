# Engineering Foundation - Setup and Agent Lab

## Safe repository setup

- Java target: 11
- Build: Maven
- Data store: in-memory H2; no production connection
- Secrets: `CAPSTONE_USERNAME` and `CAPSTONE_PASSWORD` environment variables
- Deployment: intentionally out of scope

## Reusable assets

- Project guidance: `CLAUDE.md`
- Reviewer subagent: `.claude/agents/capstone-reviewer.md`
- Reusable skill: `.claude/skills/api-quality/SKILL.md`

## Invocation examples

- Agent: ask Claude Code to use the `capstone-reviewer` agent against the current diff.
- Skill: invoke `/api-quality` after changing an endpoint.

## Small verified change

The first vertical slice creates a task with a default `TODO` status. Verification is the focused
`TaskServiceTest.createsTaskWithDefaultTodoStatus` test followed by the full suite. Actual command output
is recorded in `evidence/verification.md`.

## Reusable lesson

A narrow reviewer context (spec, traceability, security boundaries) produces more actionable findings than
an unbounded request to “review everything.”
