# Converting MERN Backend to Java Spring Boot

A comprehensive guide to recreating the MERN backend functionality using Java and Spring Boot.

---

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Project Setup](#project-setup)
3. [Database Configuration](#database-configuration)
4. [Project Structure](#project-structure)
5. [Step-by-Step Implementation](#step-by-step-implementation)
6. [Comparison: Node.js vs Java](#comparison-nodejs-vs-java)

---

## Prerequisites

### Required Tools
- **JDK 17+** (Java Development Kit)
- **Maven** or **Gradle** (Build tools)
- **Spring Boot 3.x**
- **MongoDB** (Database)
- **IDE:** IntelliJ IDEA, Eclipse, or VS Code with Java extensions

### Key Dependencies
```xml
<!-- In pom.xml for Maven -->
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Data MongoDB -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>

    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
    </dependency>

    <!-- Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- Lombok (Optional but recommended) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- BCrypt (included in Spring Security) -->
</dependencies>
```

---

## Project Setup

### Step 1: Initialize Spring Boot Project

**Option A: Using Spring Initializr (Recommended)**
1. Go to [https://start.spring.io/](https://start.spring.io/)
2. Configure:
   - **Project:** Maven
   - **Language:** Java
   - **Spring Boot:** 3.2.x (latest stable)
   - **Group:** com.springboot
   - **Artifact:** devconnector
   - **Package name:** com.springboot.devconnector
   - **Packaging:** Jar
   - **Java:** 17 or 21
3. Add Dependencies:
   - Spring Web
   - Spring Data MongoDB
   - Spring Security
   - Validation
   - Lombok
4. Generate and extract the project

**Option B: Using CLI**
```bash
# Using Spring Boot CLI
spring init --dependencies=web,data-mongodb,security,validation,lombok \
  --group=com.springboot --artifact=devconnector \
  --name=DevConnector devconnector

cd devconnector
```

### Step 2: Project Structure
```
devconnector/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── springboot/
│   │   │           └── devconnector/
│   │   │               ├── DevconnectorApplication.java
│   │   │               ├── config/
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   ├── MongoConfig.java
│   │   │               │   └── JwtConfig.java
│   │   │               ├── controllers/
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── UserController.java
│   │   │               │   ├── ProfileController.java
│   │   │               │   └── PostController.java
│   │   │               ├── models/
│   │   │               │   ├── User.java
│   │   │               │   ├── Profile.java
│   │   │               │   ├── Post.java
│   │   │               │   ├── Experience.java
│   │   │               │   ├── Education.java
│   │   │               │   └── Comment.java
│   │   │               ├── dto/
│   │   │               │   ├── request/
│   │   │               │   │   ├── LoginRequest.java
│   │   │               │   │   ├── RegisterRequest.java
│   │   │               │   │   ├── ProfileRequest.java
│   │   │               │   │   └── PostRequest.java
│   │   │               │   └── response/
│   │   │               │       ├── JwtResponse.java
│   │   │               │       ├── MessageResponse.java
│   │   │               │       └── UserResponse.java
│   │   │               ├── repositories/
│   │   │               │   ├── UserRepository.java
│   │   │               │   ├── ProfileRepository.java
│   │   │               │   └── PostRepository.java
│   │   │               ├── services/
│   │   │               │   ├── UserService.java
│   │   │               │   ├── ProfileService.java
│   │   │               │   ├── PostService.java
│   │   │               │   └── JwtService.java
│   │   │               ├── security/
│   │   │               │   ├── JwtAuthenticationFilter.java
│   │   │               │   ├── JwtTokenProvider.java
│   │   │               │   └── UserDetailsServiceImpl.java
│   │   │               └── exceptions/
│   │   │                   ├── GlobalExceptionHandler.java
│   │   │                   ├── ResourceNotFoundException.java
│   │   │                   └── UnauthorizedException.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-dev.properties
│   └── test/
│       └── java/
└── pom.xml
```

---

## Database Configuration

### application.properties
```properties
# Server Configuration
server.port=5000
spring.application.name=DevConnector

# MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=devconnector
# If using MongoDB Atlas:
# spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/devconnector

# JWT Configuration
jwt.secret=your_super_secret_jwt_key_here_make_it_long_and_complex
jwt.expiration=360000

# GitHub Token (for GitHub API)
github.token=your_github_token_here

# Logging
logging.level.com.springboot.devconnector=DEBUG
logging.level.org.springframework.security=DEBUG
```

---

## Step-by-Step Implementation

### Step 1: Create Models (Entities)

#### User.java
```java
package com.springboot.devconnector.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String name;
    private String password;
    private String avatar;
    private LocalDateTime date = LocalDateTime.now();
}
```

#### Profile.java
```java
package com.springboot.devconnector.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "profiles")
public class Profile {
    @Id
    private String id;

    @DBRef
    private User user;

    private String company;
    private String website;
    private String location;
    private String status;
    private List<String> skills = new ArrayList<>();
    private String bio;
    private String githubusername;

    private List<Experience> experience = new ArrayList<>();
    private List<Education> education = new ArrayList<>();
    private Social social = new Social();

    @Data
    public static class Social {
        private String youtube;
        private String twitter;
        private String facebook;
        private String linkedin;
        private String instagram;
    }
}
```

#### Post.java
```java
package com.springboot.devconnector.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "posts")
public class Post {
    @Id
    private String id;

    @DBRef
    private User user;

    private String text;
    private String name;
    private String avatar;
    private List<Like> likes = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private LocalDateTime date = LocalDateTime.now();

    @Data
    public static class Like {
        private String user;
    }

    @Data
    public static class Comment {
        private String id;
        private String user;
        private String text;
        private String name;
        private String avatar;
        private LocalDateTime date = LocalDateTime.now();
    }
}
```

### Step 2: Create Repositories

#### UserRepository.java
```java
package com.springboot.devconnector.repositories;

import com.springboot.devconnector.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
```

#### ProfileRepository.java
```java
package com.springboot.devconnector.repositories;

import com.springboot.devconnector.models.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
    Optional<Profile> findByUserId(String userId);
    void deleteByUserId(String userId);
}
```

#### PostRepository.java
```java
package com.springboot.devconnector.repositories;

import com.springboot.devconnector.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUserIdOrderByDateDesc(String userId);
    List<Post> findAllByOrderByDateDesc();
    void deleteAllByUserId(String userId);
}
```

### Step 3: Implement JWT Authentication

#### JwtTokenProvider.java
```java
package com.springboot.devconnector.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
```

#### JwtAuthenticationFilter.java
```java
package com.springboot.devconnector.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                String userId = tokenProvider.getUserIdFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("x-auth-token");
        return bearerToken;
    }
}
```

### Step 4: Security Configuration

#### SecurityConfig.java
```java
package com.springboot.devconnector.config;

import com.springboot.devconnector.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/profile").permitAll()
                .requestMatchers("/api/profile/user/**").permitAll()
                .requestMatchers("/api/profile/github/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
```

### Step 5: Create Services

#### UserService.java
```java
package com.springboot.devconnector.services;

import com.springboot.devconnector.dto.request.LoginRequest;
import com.springboot.devconnector.dto.request.RegisterRequest;
import com.springboot.devconnector.dto.response.JwtResponse;
import com.springboot.devconnector.dto.response.UserResponse;
import com.springboot.devconnector.exceptions.ResourceNotFoundException;
import com.springboot.devconnector.models.User;
import com.springboot.devconnector.repositories.UserRepository;
import com.springboot.devconnector.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Generate gravatar URL
        user.setAvatar(generateGravatar(request.getEmail()));

        user = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getId());
        return new JwtResponse(token);
    }

    public JwtResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(user.getId());
        return new JwtResponse(token);
    }

    public UserResponse getCurrentUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAvatar());
    }

    private String generateGravatar(String email) {
        // Implement gravatar URL generation
        String hash = org.apache.commons.codec.digest.DigestUtils.md5Hex(email.toLowerCase());
        return String.format("https://www.gravatar.com/avatar/%s?s=200&r=pg&d=mm", hash);
    }
}
```

### Step 6: Create Controllers

#### AuthController.java
```java
package com.springboot.devconnector.controllers;

import com.springboot.devconnector.dto.request.LoginRequest;
import com.springboot.devconnector.dto.request.RegisterRequest;
import com.springboot.devconnector.dto.response.JwtResponse;
import com.springboot.devconnector.dto.response.UserResponse;
import com.springboot.devconnector.security.UserPrincipal;
import com.springboot.devconnector.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserPrincipal currentUser) {
        UserResponse user = userService.getCurrentUser(currentUser.getId());
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse response = userService.loginUser(request);
        return ResponseEntity.ok(response);
    }
}
```

#### UserController.java
```java
package com.springboot.devconnector.controllers;

import com.springboot.devconnector.dto.request.RegisterRequest;
import com.springboot.devconnector.dto.response.JwtResponse;
import com.springboot.devconnector.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<JwtResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        JwtResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }
}
```

#### ProfileController.java
```java
package com.springboot.devconnector.controllers;

import com.springboot.devconnector.dto.request.ProfileRequest;
import com.springboot.devconnector.models.Profile;
import com.springboot.devconnector.security.UserPrincipal;
import com.springboot.devconnector.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<Profile> getCurrentUserProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        Profile profile = profileService.getCurrentUserProfile(currentUser.getId());
        return ResponseEntity.ok(profile);
    }

    @PostMapping
    public ResponseEntity<Profile> createOrUpdateProfile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody ProfileRequest request) {
        Profile profile = profileService.createOrUpdateProfile(currentUser.getId(), request);
        return ResponseEntity.ok(profile);
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Profile> getProfileByUserId(@PathVariable String userId) {
        Profile profile = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        profileService.deleteProfile(currentUser.getId());
        return ResponseEntity.ok().body("User deleted");
    }

    // Add more endpoints for experience, education, github repos...
}
```

#### PostController.java
```java
package com.springboot.devconnector.controllers;

import com.springboot.devconnector.dto.request.CommentRequest;
import com.springboot.devconnector.dto.request.PostRequest;
import com.springboot.devconnector.models.Post;
import com.springboot.devconnector.security.UserPrincipal;
import com.springboot.devconnector.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody PostRequest request) {
        Post post = postService.createPost(currentUser.getId(), request);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable String id) {
        postService.deletePost(currentUser.getId(), id);
        return ResponseEntity.ok().body("Post removed");
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<List<Post.Like>> likePost(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable String id) {
        List<Post.Like> likes = postService.likePost(currentUser.getId(), id);
        return ResponseEntity.ok(likes);
    }

    @PutMapping("/unlike/{id}")
    public ResponseEntity<List<Post.Like>> unlikePost(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable String id) {
        List<Post.Like> likes = postService.unlikePost(currentUser.getId(), id);
        return ResponseEntity.ok(likes);
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<List<Post.Comment>> addComment(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable String id,
            @Valid @RequestBody CommentRequest request) {
        List<Post.Comment> comments = postService.addComment(currentUser.getId(), id, request);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comment/{id}/{commentId}")
    public ResponseEntity<List<Post.Comment>> deleteComment(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable String id,
            @PathVariable String commentId) {
        List<Post.Comment> comments = postService.deleteComment(currentUser.getId(), id, commentId);
        return ResponseEntity.ok(comments);
    }
}
```

### Step 7: Create DTOs (Data Transfer Objects)

#### RegisterRequest.java
```java
package com.springboot.devconnector.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please include a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Please enter a password with 6 or more characters")
    private String password;
}
```

#### LoginRequest.java
```java
package com.springboot.devconnector.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Please include a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
```

#### JwtResponse.java
```java
package com.springboot.devconnector.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
```

### Step 8: Exception Handling

#### GlobalExceptionHandler.java
```java
package com.springboot.devconnector.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("msg", ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorized(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("msg", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("msg", ex.getMessage()));
    }
}
```

### Step 9: Run the Application

#### DevconnectorApplication.java
```java
package com.springboot.devconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevconnectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevconnectorApplication.class, args);
    }
}
```

---

## Comparison: Node.js vs Java

| Feature | Node.js (Express) | Java (Spring Boot) |
|---------|-------------------|-------------------|
| **Models** | Mongoose Schema | JPA/MongoDB Entity Classes |
| **Routes** | `router.get/post()` | `@GetMapping/@PostMapping` |
| **Middleware** | `function(req, res, next)` | Filters or Interceptors |
| **Validation** | express-validator | Jakarta Validation (`@Valid`) |
| **Auth** | JWT middleware | Spring Security + JWT Filter |
| **Database** | Mongoose | Spring Data MongoDB |
| **Dependency Injection** | `require()` | `@Autowired` or Constructor |
| **Error Handling** | try-catch + middleware | `@RestControllerAdvice` |
| **Configuration** | .env / config | application.properties |

---

## Running the Application

### Build and Run
```bash
# Using Maven
mvn clean install
mvn spring-boot:run

# Using Gradle
./gradlew clean build
./gradlew bootRun

# Or run the JAR
java -jar target/devconnector-0.0.1-SNAPSHOT.jar
```

### Testing Endpoints
```bash
# Register
curl -X POST http://localhost:5000/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"123456"}'

# Login
curl -X POST http://localhost:5000/api/auth \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"123456"}'

# Get Current User (with token)
curl -X GET http://localhost:5000/api/auth \
  -H "x-auth-token: YOUR_JWT_TOKEN"
```

---

## Additional Considerations

### 1. **CORS Configuration**
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
```

### 2. **Testing**
- Use JUnit 5 and Mockito for unit tests
- Use Spring Boot Test for integration tests
- Use TestContainers for MongoDB testing

### 3. **Documentation**
- Use Swagger/OpenAPI (springdoc-openapi)
- Add `spring-boot-starter-actuator` for health checks

### 4. **Deployment**
- Package as JAR: `mvn package`
- Deploy to: AWS Elastic Beanstalk, Heroku, Docker, Kubernetes

---

## Summary of Steps

1. ✅ Initialize Spring Boot project with dependencies
2. ✅ Configure application.properties (MongoDB, JWT)
3. ✅ Create entity models (User, Profile, Post)
4. ✅ Create repositories (interfaces extending MongoRepository)
5. ✅ Implement JWT authentication (TokenProvider, Filter)
6. ✅ Configure Spring Security
7. ✅ Create service layer (business logic)
8. ✅ Create controllers (REST endpoints)
9. ✅ Create DTOs for request/response
10. ✅ Implement global exception handling
11. ✅ Test endpoints
12. ✅ Deploy

---

## Next Steps

1. Implement remaining endpoints (experience, education, GitHub integration)
2. Add comprehensive error handling
3. Write unit and integration tests
4. Add API documentation with Swagger
5. Implement logging with SLF4J/Logback
6. Add database indexes for performance
7. Implement caching with Redis (optional)
8. Set up CI/CD pipeline

---

**Good luck with your Java Spring Boot conversion!** 🚀
