# URL Shortener Service

A robust URL shortening service built with Spring Boot that converts long URLs into short, manageable links.

## Features

- URL Shortening: Convert long URLs into short, unique identifiers
- URL Redirection: Redirect users from short URLs to original long URLs
- Persistence: Store URL mappings in a database
- Logging: Record access history
- RESTful API: Simple and intuitive API endpoints

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database (can be configured for other databases)
- Gradle
- RESTful APIs

## Prerequisites

- Java 17 or higher
- Gradle 7.x or higher

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/url_shortener_java.git
   cd url_shortener_java
   ```

2. Build the project:
   ```bash
   ./gradlew build
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

The application will start on `http://localhost:8080`

## Configuration

The application can be configured through `src/main/resources/application.properties`. Key configurations include:

- Server port
- Database settings
- URL shortening settings

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request