# Library Management Service

A Spring Boot backend REST API for managing a library system, supporting user and book management, authentication, authorization, email notifications, and JWT security.

## Features

- User registration and authentication (JWT-based)
- Role-based access control (ADMIN, USER)
- CRUD operations for Users and Books
- Borrow and return books
- Email notifications for book actions
- Validation and global exception handling
- OpenAPI/Swagger documentation

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security (JWT)
- MySQL
- Jakarta Validation
- Spring Mail (Gmail SMTP)
- Lombok
- Swagger/OpenAPI

## Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL (running on `localhost:3306` with database `librarydb`)

### Configuration

Edit [`src/main/resources/application.properties`](src/main/resources/application.properties) for DB and email credentials.

### Build & Run

```sh
mvn clean install
mvn spring-boot:run