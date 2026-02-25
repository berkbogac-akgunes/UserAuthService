# UserAuthService

Production-ready JWT-based authentication API built with Spring Boot.

## Live Demo

https://userauthservice-production-bb9f.up.railway.app

> Note: The application may take a few seconds to wake up due to cloud cold start.

---

## Overview

UserAuthService is a stateless authentication API that provides:

- User registration
- User login
- JWT token generation
- Role-based authorization (USER / ADMIN)
- Protected admin endpoints

The application is fully containerized using Docker and deployed to the cloud with Railway.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT (JJWT)
- PostgreSQL
- JPA / Hibernate
- Docker (multi-stage build)
- Docker Compose
- GitHub Actions (CI)
- Railway (Cloud Deployment)

---

## Security Features

- Stateless JWT authentication
- BCrypt password hashing
- Custom JWT authentication filter
- Role-based access control (USER / ADMIN)
- Global exception handling (400 / 401 / 409)
- Environment variable-based configuration for production

---

## API Endpoints

### Authentication

POST `/api/auth/register`  
POST `/api/auth/login`

### Admin (ROLE_ADMIN required)

GET `/api/admin/dashboard`

---

## Run Locally with Docker

Make sure Docker is installed, then run:

docker-compose up --build

The application will start on:

http://localhost:8080

---

## Environment Variables

The following environment variables must be set:

SPRING_DATASOURCE_URL  
DB_USERNAME  
DB_PASSWORD  
JWT_SECRET

Example:

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/userauth  
DB_USERNAME=postgres  
DB_PASSWORD=password  
JWT_SECRET=your-secret-key

---

## CI Pipeline

GitHub Actions automatically:

- Runs Maven tests
- Builds Docker image

Pipeline runs on every push to the main branch.

---

## Deployment

The application is deployed on Railway with a managed PostgreSQL database.

Production deployment includes:

- Docker containerization
- Cloud PostgreSQL integration
- Secure environment variable management
- HTTPS endpoint

---

## Architecture

Layered architecture:

- Controller
- Service
- Repository
- Security
- Exception Handling

Stateless REST API design with JWT-based authentication.

---

## Default Admin

On first startup, the system automatically creates a default admin user:

Username: admin  
Password: admin123

(If it does not already exist)

---

## License

This project is built for educational and portfolio purposes.