# DevConnector (MERN ➜ Java)

This repo is a modified version of the classic MERN DevConnector project. The React/Redux client stays true to the original, but the Node/Express backend has been re-imagined with Spring Boot, Spring Security, and Java 25. If you know the JavaScript-first tutorial, you can drop in here and see how the same social network feels when powered by a type-safe JVM stack.

## Table of Contents
1. [Why This Fork](#why-this-fork)
2. [Features](#features)
3. [Tech Stack](#tech-stack)
4. [Repository Layout](#repository-layout)
5. [Prerequisites](#prerequisites)
6. [Backend Setup (Java)](#backend-setup-java)
7. [Frontend Setup (React)](#frontend-setup-react)
8. [Running the Hybrid Stack](#running-the-hybrid-stack)
9. [Testing & Tooling](#testing--tooling)
10. [Helpful Docs](#helpful-docs)

## Why This Fork
- Proves how the MERN UI can talk to a non-Node backend as long as REST contracts stay intact.
- Demonstrates JWT auth, DTOs, and repository patterns in Spring Boot while preserving identical endpoints to the JavaScript version.
- Makes for a great comparison project when you want to contrast Express middleware with Spring filters, or Mongoose schemas with Spring Data MongoDB documents.

## Features
- Account lifecycle: register, login, JWT session management, profile deletion—mirrors the original MERN workflow.
- Developer profiles with experience, education, GitHub repos, and CRUD routes shared between React and Spring.
- Social feed with create/read/delete posts, threaded comments, optimistic likes.
- Unified error handling so Redux alerts and Spring `@ControllerAdvice` responses remain aligned.
- Bash helpers for API smoke tests alongside React testing scripts.

## Tech Stack
- **Backend (Java fork):** Java 25, Spring Boot 3.5, Spring Security, Spring Data MongoDB, JJWT.
- **Frontend (unchanged MERN client):** React 18, Redux Toolkit, React Router, Axios, CRA.
- **Database:** MongoDB Atlas or any MongoDB URI.
- **Tooling:** Maven wrapper (`mvnw`), pnpm/npm scripts, curl-based smoke tests.

## Repository Layout
```
.
├── client/                     # Original MERN React SPA
│   ├── package.json
│   └── src/
├── server/
│   └── devconnector/           # Spring Boot drop-in replacement for Express API
│       ├── pom.xml
│       ├── src/main/java/com/springboot/devconnector
│       │   ├── controllers/    # Auth, profile, posts
│       │   ├── services/, dto/, models/, repositories/, security/, config/
│       ├── src/main/resources/application.properties
│       └── test-api.sh         # Simple curl-based integration script
├── FRONTEND_ENDPOINTS.md
└── GUIDE.md
```

## Prerequisites
- **Java:** JDK 25 (or 21+ with updated compiler settings).
- **Maven:** Use the bundled wrapper or Maven 3.9+.
- **Node.js:** v18+ to match CRA tooling.
- **MongoDB:** Hosted or local instance; update `spring.data.mongodb.uri`.
- **Secrets:** JWT secret + Mongo URI stored in `application.properties` or environment variables.

## Backend Setup (Java)
1. Configure secrets in `server/devconnector/src/main/resources/application.properties`:
   - `spring.data.mongodb.uri`
   - `app.jwtSecret`
   - `app.jwtExpirationInMs`
   - `server.port` (defaults to `5001`, which the client proxy targets)
2. Install dependencies and run Spring Boot:
   ```bash
   cd server/devconnector
   ./mvnw spring-boot:run
   ```
3. Optional build/test commands:
   ```bash
   ./mvnw clean package
   ./mvnw test
   ```

## Frontend Setup (React)
1. Install packages:
   ```bash
   cd client
   pnpm install    # use npm/yarn if you prefer
   ```
2. Start the CRA dev server:
   ```bash
   pnpm start
   ```
   The proxy forwards `/api/*` to `http://localhost:5001`, so the Java API needs to be running.
3. Build when ready to deploy:
   ```bash
   pnpm run build
   ```

## Running the Hybrid Stack
1. Ensure MongoDB is reachable.
2. Start Spring Boot via `./mvnw spring-boot:run`.
3. Start React via `pnpm start`.
4. Visit `http://localhost:3000`, create an account, and confirm the UI behaves the same as the original MERN project.

## Testing & Tooling
- **Backend smoke tests:** `server/devconnector/test-api.sh` (make it executable first).
- **Backend tests:** `./mvnw test`.
- **Frontend tests:** `pnpm test`.
- **Contract reference:** `FRONTEND_ENDPOINTS.md` documents every React action ↔ API route pairing preserved from MERN.

## Helpful Docs
- `GUIDE.md` – detailed explanation of the Spring Boot architecture and how it maps to the MERN tutorial steps.
- `FRONTEND_ENDPOINTS.md` – quick lookup for matching React actions to Spring controllers.

This fork is meant to help MERN developers dip into Java without rewriting the UI. Swap backends freely, compare patterns, and ship whichever stack fits your team.*** End Patch_errno 2
