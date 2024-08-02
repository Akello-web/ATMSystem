# Automated Teller Machine (ATM) System

## Overview
The Automated Teller Machine (ATM) System is a secure and efficient banking application built using Java and Spring technologies. This project simulates key functionalities of a real-world ATM, providing users with secure access to their accounts for transactions such as withdrawals, deposits, balance inquiries, and fund transfers between accounts.

## Features
- **User Authentication**: Secure login using account number and PIN.
- **Session Management**: Ensures secure sessions with automatic timeout and explicit logout functionality.
- **Account Operations**:
  - Check Balance
  - Deposit
  - Withdraw
  - Transfer Funds
- **Admin Functions**:
  - View Transaction Logs
  - Manage System Issues (e.g., replenishing cash, resolving transaction errors)
  - Update Account Information

## Technologies Used
- **Java**: Programming language for backend development.
- **Spring Boot**: Facilitates rapid development and integration with its auto-configuration and starter dependencies.
- **Spring Security**: Provides robust authentication and authorization mechanisms to secure endpoints and manage user sessions.
- **Spring Data JPA**: Implements data access and persistence layer for managing user accounts and transaction records.
- **PostgreSQL**: Relational database management system for storing user and transaction data securely.
- **Liquibase**: An open-source tool for keeping track of database schema script revisions.
- **Swagger/OpenAPI**: Documentation tool for designing, building, documenting, and consuming RESTful APIs.
- **JUnit**: Testing framework for unit testing of Java applications.
- **Mockito**: Mocking framework for unit tests in Java.
- **Gradle**: Build automation tool for managing dependencies and building Java applications.

## Architecture
### Backend Framework
- **Spring Boot**: Facilitates rapid development and integration with its auto-configuration and starter dependencies.

### Security
- **Spring Security**: Provides robust authentication and authorization mechanisms to secure endpoints and manage user sessions.

### Data Management
- **Spring Data JPA**: Implements data access and persistence layer for managing user accounts and transaction records.

### Database Schema
- **Users Table**: Stores user information including id, account number, PIN (encrypted), and account balance.
- **Transactions Table**: Logs transaction details such as id, type (withdrawal, deposit, transfer), amount, date, and account number involved.

### API Endpoints
#### Authentication
- `POST /auth/login`: Authenticate user by account number and PIN, creates a session.
- `POST /auth/logout`: Terminate the current session securely.

#### Account Operations
- `GET /accounts/balance`: Retrieve current account balance.
- `POST /accounts/deposit`: Deposit funds into the account.
- `POST /accounts/withdraw`: Withdraw funds from the account.
- `POST /accounts/transfer`: Transfer funds to another account.

#### Admin Functions
- `GET /admin/transactions`: Retrieve all transaction logs for monitoring and management purposes.

## Getting Started

To run the Telemedicine System locally:

1. Clone this repository.
2. Configure PostgreSQL database settings in `application.properties`.
3. Build and run the application using Gradle.
4. Access the API documentation via Swagger UI (`http://localhost:8088/swagger-ui.html`).

