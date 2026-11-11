
# TaskFlow API

TaskFlow is a backend project I built using Java and Spring Boot to practice developing REST APIs.

The application allows users to register, log in, and manage tasks. Each task has a title, description, status, and priority.

While working on this project, I focused on API structure, database integration, authentication, validation, and testing.

## Technologies

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA, Hibernate
- PostgreSQL
- JWT
- Maven
- Docker, Docker Compose
- Swagger / OpenAPI
- JUnit 5, Mockito

## What the application does

**Authentication**
- User registration and login
- Password hashing with BCrypt
- JWT-based authentication
- Protected task endpoints

**Task management**
- Create, update, delete, and retrieve tasks
- Set task status: TODO, IN_PROGRESS, DONE
- Set task priority: LOW, MEDIUM, HIGH
- Filter tasks by status and priority
- Pagination and sorting

**Other functionality**
- DTOs for API requests and responses
- Request validation
- Global exception handling
- API documentation with Swagger
- Unit tests for authentication logic

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register a user |
| POST | /api/auth/login | Log in and get a JWT token |

### Tasks

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/tasks | Get tasks, filter and sort results |
| GET | /api/tasks/{id} | Get a task by ID |
| POST | /api/tasks | Create a task |
| PUT | /api/tasks/{id} | Update a task |
| DELETE | /api/tasks/{id} | Delete a task |

All task endpoints require a valid JWT token.

## Running the project

The easiest way to run the application is with Docker Compose.

You will need Docker and Docker Compose installed.

**1. Clone the repository**

```bash
git clone https://github.com/Opyr-Vasylyna/taskflow-api.git
cd taskflow-api
```

**2. Create a `.env` file**

Add the following variables:

```env
DB_USERNAME=taskflow
DB_PASSWORD=your_password
JWT_SECRET=your_jwt_secret
```

You can generate a JWT secret with:

```bash
openssl rand -hex 32
```

**3. Start the application**

```bash
docker compose up --build -d
```

Once the containers are running, the API is available at:

http://localhost:8080

Swagger UI:

http://localhost:8080/swagger-ui/index.html

The PostgreSQL container is accessible from the host on port 5433.

**4. Stop the containers**

```bash
docker compose down
```

## Example Request

Create a task using `POST /api/tasks`:

```json
{
  "title": "Finish Spring Boot project",
  "description": "Add tests and update documentation",
  "status": "IN_PROGRESS",
  "priority": "HIGH"
}
```

A valid JWT token is required to access this endpoint.

## Testing

I used JUnit 5 and Mockito to test the authentication service and JWT functionality.

To run the tests:

```bash
./mvnw clean test
```

## Notes

The project uses PostgreSQL for data storage and Spring Data JPA for database operations.

I used Spring Security with JWT authentication to protect the task endpoints. Passwords are stored as BCrypt hashes.

Swagger UI is included so the endpoints can be tested directly in the browser.

Docker Compose runs the application and PostgreSQL together, so there is no need to configure a local database manually.

## Author

Vasylyna Opyr

GitHub: https://github.com/Opyr-Vasylyna
