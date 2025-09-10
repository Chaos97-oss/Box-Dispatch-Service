# Box Dispatch Service (Refactor V2)

This is a Spring Boot project implementing the Box Dispatch Service assessment.
It includes:
- H2 in-memory DB with sample data (data.sql)
- Entities: Box, Item, BoxState
- Repository layer (Spring Data JPA)
- Service: BoxService (interface) + BoxServiceImpl (implementation)
- Controller returning ApiResponse<T>
- Global exception handler
- Unit tests (MockMvc)

## Build & Run
Requirements: Java 17+, Maven

```bash
unzip box-dispatch-service-refactor-v2.zip
cd box-dispatch-service-refactor-v2
mvn clean package
mvn spring-boot:run
```

Run tests:
```bash
mvn test
```

H2 console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:boxdb)
