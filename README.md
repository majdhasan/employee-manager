
# Employee Manager

This is a Spring Boot application that provides a RESTful API for managing employees. It uses a Postgresql database to store employee data and Kafka for messaging.

## Build and Run

To build and run this application, follow these steps:

1.  Clone this repository.
2.  If needed, update the configuration in the `application.properties` file.
3.  Build the application using Maven: `mvn clean install`.
4.  Run the application using Maven: `mvn spring-boot:run`.

## Testing

To run the tests, use the following command: `mvn test`.

## API Endpoints

This application provides the following RESTful API endpoints:

-   `GET /employees`: Returns a list of all employees.
-   `GET /employees/{uuid}`: Returns the employee with the specified UUID.
-   `POST /employees`: Creates a new employee.
-   `PUT /employees/{uuid}`: Updates the employee with the specified UUID.
-   `DELETE /employees/{uuid}`: Deletes the employee with the specified UUID.

## Authentication

This section provides information about the authentication endpoints and their usage.

### Overview

The AuthenticationController is a REST controller that provides two endpoints for user authentication:

-   `/auth/register`: allows users to register by providing their details (username and password)
-   `/auth/authenticate`: allows registered users to authenticate by providing their username and password

Both endpoints accept POST requests and return an `AuthenticationResponse` object, which contains an authentication token that can be used to authorize subsequent requests.

### Usage

To use the authentication endpoints, follow these steps:

1.  Register a user: Send a POST request to `/auth/register` endpoint with a JSON payload containing the username and password. The `RegisterRequest` object expects the following fields:

    `{
    "username": "user",
    "password": "password123"
    }`

    If the registration is successful, the response will contain a `200 OK` status code and an `AuthenticationResponse` object that includes an authentication token.

2.  Authenticate a user: Send a POST request to `/auth/authenticate` endpoint with a JSON payload containing the username and password. The `AuthenticationRequest` object expects the following fields:

    `{
    "username": "user",
    "password": "password123"
    }`

    If the authentication is successful, the response will contain a `200 OK` status code and an `AuthenticationResponse` object that includes an authentication token.

    **Note:** To authorize subsequent requests, include the authentication token in the request header using the `Authorization` field. The value should be in the format `Bearer <token>`. For example:


    `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIn0.BY-ZyJ_ZL-7ctd1sFzX19v_zP97-JpAZdlIgcx0-8Nw`