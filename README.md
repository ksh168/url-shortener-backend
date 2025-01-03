# URL Shortener Service

A robust URL shortening service built with Spring Boot that converts long URLs into short, manageable links.

## Features

- URL Shortening
  - Convert long URLs into short, unique identifiers
  - Custom alias support (3-32 characters)
  - Secure random generation for short URLs
  - URL validation and sanitization
  - Blocked domain protection

- URL Redirection
  - Fast redirection from short URLs to original URLs using redis cache
  - Support for HTTP and HTTPS protocols
  - URL length validation (max 2048 characters)
  - Protection against malformed URLs

- Security
  - Input validation and sanitization
  - Protection against reserved aliases
  - Blocked domain list
  - Secure random URL generation

- Caching & Performance
  - Redis caching support
  - Asynchronous access logging

- Persistence
  - Database storage for URL mappings
  - Access logging and analytics

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Redis Cache
- H2 Database (can be configured for other databases)
- Gradle
- RESTful APIs

## Prerequisites

- Java 17 or higher
- Gradle 7.x or higher
- Redis (optional, for caching)

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

## API Endpoints

- `POST /api/shorten` - Create a short URL
- `GET /{shortUrl}` - Redirect to original URL
- `GET /api/health/stats` - Check service health

## Configuration

The application can be configured through `src/main/resources/application.properties`. Key configurations include:

- Server port
- Database settings
- Redis cache settings
- URL shortening settings
- Blocked domains list
- Reserved aliases

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request