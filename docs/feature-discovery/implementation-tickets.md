# Implementation Tickets

## CAP-001 - Establish safe project baseline

**Depends on:** none  
**Acceptance:** Java 11 Maven application starts with environment-provided credentials; H2 is not exposed.

## CAP-002 - Create task vertical slice

**Depends on:** CAP-001  
**Covers:** AC-01, AC-02, AC-03, AC-07, AC-08  
**Acceptance:** authenticated valid create returns 201; invalid input returns 400; unit and API tests pass.

## CAP-003 - Query tasks

**Depends on:** CAP-002  
**Covers:** AC-04, AC-07  
**Acceptance:** list/get contracts are stable and missing task returns generic 404.

## CAP-004 - Update task status

**Depends on:** CAP-002  
**Covers:** AC-05, AC-06, AC-07  
**Acceptance:** valid enum persists; missing/invalid status is rejected.

## CAP-005 - Quality gates and QA handoff

**Depends on:** CAP-001 through CAP-004  
**Acceptance:** suite passes, AI/security reports contain no open critical/high, reviewer record exists, QA and
risk matrices are complete.

## CAP-007 - Filter tasks by status

**Depends on:** CAP-003

**Covers:** AC-09

**Acceptance:** authenticated callers can optionally filter the list by a valid task status; an omitted filter
returns all tasks and an unknown filter returns a safe 400 response.
