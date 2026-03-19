# api-springboot-security-and-JWT

A RESTful API for a blog system built with Spring Boot, Spring Security, and JWT authentication.

## Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security** + JWT
- **Spring Data JPA** + Hibernate
- **MySQL**
- **ModelMapper**

## Getting Started

### Prerequisites

- Java 17+
- MySQL running on `localhost:3306`
- Maven

### Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE systemblog;
```

### Configuration

Edit `src/main/resources/application.yml` and set your MySQL credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/systemblog
    username: your_username
    password: your_password
```

### Run

```bash
mvn spring-boot:run
```

The server starts on **port 8081**.

---

## Authentication

All write operations (except `GET`) require a `Bearer` JWT token in the `Authorization` header.

### Register

```
POST /api/auth/register
```

```json
{
  "name": "john",
  "email": "john@example.com",
  "password": "secret123"
}
```

### Login

```
POST /api/auth/login
```

```json
{
  "usernameOrEmail": "john",
  "password": "secret123"
}
```

**Response:**

```json
{
  "accessToken": "<jwt_token>",
  "tokenType": "Bearer"
}
```

Use the token in subsequent requests:

```
Authorization: Bearer <jwt_token>
```

---

## API Endpoints

### Posts

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/api/posts` | No | Get all posts (paginated) |
| `GET` | `/api/posts/{id}` | No | Get post by ID |
| `POST` | `/api/posts` | ADMIN | Create a post |
| `PUT` | `/api/posts` | Yes | Update a post |
| `DELETE` | `/api/posts/{id}` | ADMIN | Delete a post |

**Query parameters for `GET /api/posts`:**

| Param | Default | Description |
|-------|---------|-------------|
| `pageNo` | `0` | Page number |
| `pageSize` | `10` | Items per page |
| `sortBy` | `id` | Field to sort by |
| `sortDir` | `asc` | Sort direction (`asc` / `desc`) |

**Post body:**

```json
{
  "title": "My First Post",
  "description": "A short description",
  "content": "Full content of the post..."
}
```

---

### Comments

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/api/posts/{postId}/comments` | No | Get all comments for a post |
| `GET` | `/api/posts/{postId}/comments/{id}` | No | Get comment by ID |
| `POST` | `/api/posts/{postId}/comments` | Yes | Create a comment |
| `PUT` | `/api/posts/{postId}/comments` | Yes | Update a comment |
| `DELETE` | `/api/posts/{postId}/comments/{id}` | Yes | Delete a comment |

**Comment body:**

```json
{
  "name": "Jane",
  "email": "jane@example.com",
  "body": "Great post! Very informative."
}
```

---

## Project Structure

```
src/main/java/com/sistema/blog/
├── configuration/       # Security configuration
├── controller/          # REST controllers (Post, Comment, Auth)
├── dto/                 # Data Transfer Objects
├── entities/            # JPA entities (Post, Comment, User, Role)
├── exceptions/          # Global exception handling
├── repository/          # Spring Data JPA repositories
├── security/            # JWT filter, token provider, entry point
├── service/             # Business logic interfaces and implementations
└── utils/               # Constants and utilities
```

## Error Responses

All errors return the following structure:

```json
{
  "timestamp": "2026-03-15T10:00:00.000+00:00",
  "message": "Post not found with: id: '99'",
  "details": "uri=/api/posts/99"
}
```
