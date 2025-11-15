# DevConnector Java Backend Guide

This document summarizes the steps you need to create, configure, and run the Java/Spring backend that replaces the original Node/Express API from the MERN DevConnector project. Follow these steps end-to-end and you will have a drop-in backend that keeps the React client untouched.

## 1. Prerequisites
- **Java 25** (or a recent LTS, e.g., 21) with `JAVA_HOME` set.
- **Maven 3.9+** or simply use the wrapper (`./mvnw` / `mvnw.cmd`).
- **MongoDB 6+** (Atlas URI or local instance).
- **Node.js 18+** to run the React client for contract validation.
- Optional: Docker (if you want to run Mongo in a container).

## 2. Project Layout
```
server/devconnector
├── pom.xml
├── src/main/java/com/springboot/devconnector
│   ├── config/             # Spring configuration (SecurityConfig, etc.)
│   ├── controllers/        # REST controllers (AuthController, ProfileController, PostController)
│   ├── dto/                # Request/response payloads
│   ├── exceptions/         # GlobalExceptionHandler and custom errors
│   ├── models/             # MongoDB documents (User, Profile, Post, Like, Comment)
│   ├── repositories/       # Spring Data interfaces
│   ├── security/           # JWT filters, providers, user details
│   └── services/           # Business logic (AuthService, ProfileService, PostService)
└── src/main/resources/application.properties
```

## 3. Configure Environment
Edit `src/main/resources/application.properties` and provide:
```properties
spring.data.mongodb.uri=mongodb+srv://<user>:<password>@<cluster>/<db>?retryWrites=true&w=majority
app.jwtSecret=<generate-strong-secret>
app.jwtExpirationInMs=3600000
server.port=5001
```
Keep these values aligned with the React client proxy (port `5001`) so API calls resolve correctly.

## 4. Generate Domain + Repositories
1. Model MongoDB documents under `models/` (e.g., `Post.java`, `Comment.java`, `Like.java`). Use `@Document`, `@Id`, and `@DBRef` annotations to mirror the Mongoose schemas from the MERN app.
2. Create Spring Data repositories (e.g., `PostRepository`, `UserRepository`) that extend `MongoRepository`. Add custom queries such as `List<Post> findAllByOrderByDateDesc();`.

## 5. Create DTOs
- Under `dto/`, introduce request/response payloads that match what the React Redux actions expect. Examples: `PostRequest`, `PostResponse`, `CommentRequest`, `CommentResponse`, `LikeResponse`.
- Use Jackson annotations (`@JsonProperty("_id")`) to keep field names (_id vs id) identical to the MERN API.

## 6. Services
Implement service classes that encapsulate business rules:
- `AuthService`, `UserService`, `ProfileService`, and `PostService` live in `services/`.
- The Post workflow (`PostService.java`) demonstrates how to translate MERN logic into Spring:
  - `createPost`, `getAllPosts`, `deletePost`, `likePost`, `unlikePost`, `addComment`, and `deleteComment` mirror the behavior of `routes/api/posts.js` from the MERN repo.
  - Helper methods (`postToResponse`, `mapLikes`, `mapComments`) keep DTO mapping centralized.
- Throw meaningful exceptions (currently `RuntimeException`) so `GlobalExceptionHandler` can respond with JSON bodies similar to the original Express middleware.

## 7. Controllers
Expose REST endpoints under `controllers/`:
- `PostController.java` at `/api/posts` keeps all routes private and returns DTOs that the React client already understands.
- Annotate methods with Spring MVC annotations (`@GetMapping`, `@PostMapping`, etc.) and inject `@AuthenticationPrincipal UserDetails` to retrieve the current user’s email/ID from the JWT.
- Keep URL patterns identical to the MERN version (`/api/posts`, `/api/posts/like/{postId}`, `/api/posts/comment/{postId}`, etc.).

## 8. Security & Authentication
1. Configure JWT support in `security/`:
   - `JwtAuthenticationFilter`, `JwtTokenProvider`, and `CustomUserDetailsService` validate and parse tokens.
2. Update `SecurityConfig` to:
   - Require authentication on `/api/posts/**`, `/api/profile/**`, etc.
   - Permit `/api/auth/**` and `/api/users` for registration/login.
3. Ensure the React client stores the JWT exactly like in the MERN tutorial; the Spring backend must accept the `x-auth-token` header.

## 9. Build & Run
```bash
cd server/devconnector
./mvnw clean package      # optional compilation check
./mvnw spring-boot:run    # start the API on http://localhost:5001
```
The first run downloads all Maven dependencies; subsequent starts are faster.

## 10. Validate Against the MERN Client
1. Start MongoDB and ensure the URI in `application.properties` is reachable.
2. In another terminal, start the React client (`cd client && pnpm start`).
3. Verify flows:
   - Register/login (`/register`, `/login`).
   - Edit profile, add experience/education.
   - Create, like/unlike, and comment on posts.
4. For quick smoke tests, use `server/devconnector/test-api.sh` or Postman with the documented endpoints in `FRONTEND_ENDPOINTS.md`.

## 11. Testing & Next Steps
- Run backend tests: `./mvnw test`.
- Add unit/integration tests for new services (e.g., `PostServiceTests` with MongoDB test slices).
- Replace generic `RuntimeException` with custom exceptions and map them in `GlobalExceptionHandler`.
- Review `PROFILE_STEPS.md` and `POST_IMPLEMENTATION_GUIDE.md` for deeper walkthroughs of each feature area.

Following these steps gives you a fully functional Spring Boot backend that preserves the MERN API contract. Build additional features by following the same DTO → Service → Controller pattern, always verifying behavior against the React client.
