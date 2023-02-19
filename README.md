
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