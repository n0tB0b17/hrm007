# HRM007 - Human Resource Management System

[![Swagger UI](https://img.shields.io/badge/Swagger-UI-blue)](http://localhost:8080/swagger-ui/index.html)
[![Neo4j](https://img.shields.io/badge/Neo4j-GraphDB-green)](https://neo4j.com/)

HRM007 is a modern, scalable Human Resource Management System built with Spring Boot and Neo4j graph database. This system provides comprehensive HR management capabilities with a focus on efficient data relationships and intuitive API documentation.

## Features

- 📊 Comprehensive Employee Management
- 🏢 Department Organization
- 🎓 Position Management
- 🔐 Role-based Access Control
- 🏠 Multi-tenant Support
- 📚 Neo4j Graph Database Integration
- 📖 Swagger/OpenAPI Documentation

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/_7/hr/
│   │       ├── controller/     # REST API Controllers
│   │       ├── domain/         # Neo4j Domain Models
│   │       ├── repository/     # Neo4j Repositories
│   │       └── config/         # Application Configuration
│   └── resources/
│       └── application.properties
└── test/
```

## Prerequisites

- Java 17 or higher
- Maven or Gradle
- Neo4j Graph Database (4.4.x or higher)
- Spring Boot 3.x

## Configuration

The application requires the following configuration in `application.properties`:

```properties
# Neo4j configuration
spring.neo4j.url=bolt://localhost:7687
spring.neo4j.authentication.username=neo4j
spring.neo4j.authentication.password=password123

# Server configuration
server.port=8080
```

## Getting Started

1. Clone the repository:
```bash
git clone [repository-url]
cd hrm007
```

2. Build the project:
```bash
./gradlew build
```

3. Run the application:
```bash
./gradlew bootRun
```

## API Documentation

The application includes comprehensive Swagger/OpenAPI documentation that can be accessed at:

```
http://localhost:8080/swagger-ui/index.html
```

## Database Setup

1. Install Neo4j Desktop or Neo4j Server
2. Configure the database with the credentials specified in `application.properties`
3. Ensure the Neo4j service is running before starting the application

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please open an issue in the GitHub repository or contact the project maintainers.

## Acknowledgments

- Spring Boot Framework
- Neo4j Graph Database
- Swagger/OpenAPI
- All contributors who helped shape this project
