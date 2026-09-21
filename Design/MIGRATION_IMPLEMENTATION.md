# OPOPPR migration implementation

This document records what has been implemented from
[`MIGRATION_ANALYSIS_WORD.md`](MIGRATION_ANALYSIS_WORD.md) and keeps the migration safe to
continue in small vertical slices.

## Decoupling boundary

The new application is isolated from JSF at two boundaries:

1. `apps/api` exposes versioned REST resources. Controllers accept and return DTOs rather than
   exposing persistence objects.
2. `apps/web` consumes only those REST resources through RTK Query. The Axios client owns the
   base URL, access-token interceptor, and unauthorized-session cleanup.

The legacy `opoppr` WAR remains in the repository for behavior comparison until each workflow
has a replacement and an acceptance test.

## First vertical slice

- Login using the existing bill-number/PIN mental model, with stateless signed access tokens.
- Dashboard form summaries and status counts.
- LAT5 property-asset editor with Handsontable.
- Spreadsheet context-menu actions: insert above, insert below, delete row, undo, redo, and alignment.
- Bean validation for row data and structured validation errors.
- Password-reset email composition with Thymeleaf and `JavaMailSender`.
- Health, info, and metrics endpoints through Spring Boot Actuator.
- MySQL and Mailpit development services through Docker Compose.

`FormService` currently seeds a deterministic dataset so the UI and API can be developed
without mutating the historical database. The next persistence slice should replace that
adapter with repositories mapped to the existing `FORM`, `NOA_PP_LAT5`, and
`NOA_PP_LAT5_FILING` tables, then run both adapters in parallel during cutover.

## Test layers

| Layer | Location | Coverage |
| --- | --- | --- |
| Frontend unit | `apps/web/src/**/*.test.*` | Redux session transitions |
| Frontend integration | `apps/web/src/**/*.integration.test.*` | Login form, RTK mutation, local session |
| Frontend E2E | `apps/web/e2e` | Sign in and open LAT5 workflow with API route fixtures |
| Backend unit | `apps/api/src/test/.../TokenServiceTest.java` | Token signing and tamper rejection |
| Backend integration | `apps/api/src/test/.../FormControllerIntegrationTest.java` | Security, validation context, dashboard/form API |
| Backend E2E | `apps/api/src/test/.../OpopprApiE2ETest.java` | Testcontainers MySQL reachability |

Backend E2E is intentionally gated with `-Drun.e2e=true` because it requires a Docker runtime.