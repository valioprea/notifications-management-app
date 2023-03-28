## Notification management application

- Java + Spring Boot Application
- H2 Relational Database
- JDBC used for querying the database

### Application Structure
- DAO + DTO patterns were used
- A controller as entry point, a service layer and a persistence layer
- Abstraction for decoupling the DAO and the service
- Generic exception handling for some db accessing methods

### Extras
- Swagger available at: http://localhost:8080/swagger-ui/index.html
- H2 embedded database at: http://localhost:8080/h2-console {user=root, password=pass}
- Postman collection available in the repository

### How to run
- Download locally, import maven projects & just run and have fun