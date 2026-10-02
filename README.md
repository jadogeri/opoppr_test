# OPOPPR platform

This repository contains the original Orleans Parish Online Personal Property Reporting
(OPOPPR) system and its incremental migration to a decoupled Turborepo application.

## Repository map

| Directory | Purpose |
| --- | --- |
| `apps/web` | Next.js + TypeScript client with Redux Toolkit, RTK Query, Axios, and Handsontable |
| `apps/api` | Spring Boot REST API with Security, Validation, Mail, Thymeleaf, Actuator, and Docker Compose support |
| `packages/contracts` | Shared TypeScript API contracts |
| `Design` | Migration analysis and system design documentation |
| `Database` | Schema exports, source data, and database migration assets |
| `opoppr` | Legacy JSF/PrimeFaces application retained for comparison during migration |

The migration follows the analysis in
[`Design/MIGRATION_ANALYSIS_WORD.md`](Design/MIGRATION_ANALYSIS_WORD.md). The first vertical
slice exposes authentication, dashboard form summaries, the LAT5 spreadsheet workflow, asset
lookup, and password-reset email composition through versioned `/api/v1` routes. More JSF
pages can be moved behind the same API boundary without coupling the frontend to Java
templates or persistence classes.

## Development

Requirements: Node.js 20+, pnpm 10+, Java 17+, Maven 3.9+, and Docker for MySQL/Mailpit.

```bash
pnpm install
docker compose up -d mysql mailpit
pnpm dev
```

The web app runs at `http://localhost:3000`, the API at `http://localhost:8081`, and Mailpit at
`http://localhost:8025`.

Run the verification layers with:

```bash
pnpm build
pnpm test:unit
pnpm test:integration
pnpm test:e2e
cd apps/api && mvn verify
```

Backend E2E tests are tagged and gated behind `-Drun.e2e=true` because they require
Docker/Testcontainers. The legacy instructions remain in [`opoppr/README.md`](opoppr/README.md).
