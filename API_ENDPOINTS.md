# API Endpoints Documentation

Complete list of all API endpoints in the MERN application.

## Base URL
```
http://localhost:5000
```

---

## Authentication & Users

### Register User
- **Endpoint:** `POST /api/users`
- **Access:** Public
- **Description:** Register a new user
- **Body:**
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string (min 6 characters)"
  }
  ```
- **Returns:** JWT token

### Get Authenticated User
- **Endpoint:** `GET /api/auth`
- **Access:** Private
- **Description:** Get current authenticated user data
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Returns:** User object (without password)

### Login User
- **Endpoint:** `POST /api/auth`
- **Access:** Public
- **Description:** Authenticate user and get token
- **Body:**
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Returns:** JWT token

---

## Profile Management

### Get Current User's Profile
- **Endpoint:** `GET /api/profile/me`
- **Access:** Private
- **Description:** Get current user's profile
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Returns:** Profile object with user details

### Create or Update Profile
- **Endpoint:** `POST /api/profile`
- **Access:** Private
- **Description:** Create or update user profile
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Body:**
  ```json
  {
    "status": "string (required)",
    "skills": "string (required, comma-separated)",
    "company": "string",
    "website": "string",
    "location": "string",
    "bio": "string",
    "githubusername": "string",
    "youtube": "string",
    "facebook": "string",
    "twitter": "string",
    "instagram": "string",
    "linkedin": "string"
  }
  ```
- **Returns:** Profile object

### Get All Profiles
- **Endpoint:** `GET /api/profile`
- **Access:** Public
- **Description:** Get all user profiles
- **Returns:** Array of profile objects

### Get Profile by User ID
- **Endpoint:** `GET /api/profile/user/:user_id`
- **Access:** Public
- **Description:** Get specific user's profile by user ID
- **Parameters:** `user_id` - MongoDB ObjectId
- **Returns:** Profile object

### Delete Profile, User & Posts
- **Endpoint:** `DELETE /api/profile`
- **Access:** Private
- **Description:** Delete profile, user account, and all associated posts
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Returns:** Success message

### Add Experience
- **Endpoint:** `PUT /api/profile/experience`
- **Access:** Private
- **Description:** Add work experience to profile
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Body:**
  ```json
  {
    "title": "string (required)",
    "company": "string (required)",
    "location": "string",
    "from": "date (required)",
    "to": "date",
    "current": "boolean",
    "description": "string"
  }
  ```
- **Returns:** Updated profile object

### Delete Experience
- **Endpoint:** `DELETE /api/profile/experience/:exp_id`
- **Access:** Private
- **Description:** Remove experience from profile
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Parameters:** `exp_id` - Experience ID
- **Returns:** Updated profile object

### Add Education
- **Endpoint:** `PUT /api/profile/education`
- **Access:** Private
- **Description:** Add education to profile
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Body:**
  ```json
  {
    "school": "string (required)",
    "degree": "string (required)",
    "fieldofstudy": "string (required)",
    "from": "date (required)",
    "to": "date",
    "current": "boolean",
    "description": "string"
  }
  ```
- **Returns:** Updated profile object

### Delete Education
- **Endpoint:** `DELETE /api/profile/education/:edu_id`
- **Access:** Private
- **Description:** Remove education from profile
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Parameters:** `edu_id` - Education ID
- **Returns:** Updated profile object

### Get GitHub Repos
- **Endpoint:** `GET /api/profile/github/:username`
- **Access:** Public
- **Description:** Get user's GitHub repositories
- **Parameters:** `username` - GitHub username
- **Returns:** Array of GitHub repositories (latest 5)

---

## Posts & Social Features

### Create Post
- **Endpoint:** `POST /api/posts`
- **Access:** Private
- **Description:** Create a new post
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Body:**
  ```json
  {
    "text": "string (required)"
  }
  ```
- **Returns:** Post object

### Get All Posts
- **Endpoint:** `GET /api/posts`
- **Access:** Private
- **Description:** Get all posts (sorted by date, newest first)
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Returns:** Array of post objects

### Get Post by ID
- **Endpoint:** `GET /api/posts/:id`
- **Access:** Private
- **Description:** Get specific post by ID
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Parameters:** `id` - Post ID
- **Returns:** Post object

### Delete Post
- **Endpoint:** `DELETE /api/posts/:id`
- **Access:** Private
- **Description:** Delete a post (user must be post owner)
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Parameters:** `id` - Post ID
- **Returns:** Success message

### Like Post
- **Endpoint:** `PUT /api/posts/like/:id`
- **Access:** Private
- **Description:** Like a post
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Parameters:** `id` - Post ID
- **Returns:** Array of likes

### Unlike Post
- **Endpoint:** `PUT /api/posts/unlike/:id`
- **Access:** Private
- **Description:** Remove like from a post
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Parameters:** `id` - Post ID
- **Returns:** Array of likes

### Add Comment
- **Endpoint:** `POST /api/posts/comment/:id`
- **Access:** Private
- **Description:** Add comment to a post
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Parameters:** `id` - Post ID
- **Body:**
  ```json
  {
    "text": "string (required)"
  }
  ```
- **Returns:** Array of comments

### Delete Comment
- **Endpoint:** `DELETE /api/posts/comment/:id/:comment_id`
- **Access:** Private
- **Description:** Delete comment from post (user must be comment owner)
- **Headers:** `x-auth-token: <JWT_TOKEN>`
- **Parameters:**
  - `id` - Post ID
  - `comment_id` - Comment ID
- **Returns:** Array of comments

---

## Summary

**Total Endpoints:** 20

**By Category:**
- Authentication & Users: 3 endpoints
- Profile Management: 10 endpoints
- Posts & Social: 7 endpoints

**By Access Level:**
- Public: 6 endpoints
- Private: 14 endpoints

**Authentication:**
All private endpoints require a JWT token in the `x-auth-token` header. Obtain the token by registering or logging in through the auth endpoints.
