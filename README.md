# My Spring App

A Spring Boot application with a comprehensive set of features including web interface, database integration, security, and email functionality.

## 🚀 Features

- Spring Boot 3.4.5
- Java 21
- Spring Data JPA for database operations
- MySQL database integration
- Thymeleaf templating engine
- Spring Security for authentication and authorization
- Spring Mail for email functionality
- Log4j2 for advanced logging
- Spring DevTools for development convenience
- Input validation support

## 📋 Prerequisites

- Java 21 or higher
- Maven
- MySQL Server

## 🛠️ Installation

1. Clone the repository:
```bash
git clone [https://github.com/ALKorzh/my-spring-app.git]
cd my-spring-app
```

2. Configure MySQL database:
   - Create a new database
   - Update `application.properties` with your database credentials

3. Build the project:
```bash
./mvnw clean install
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080`

## 🔧 Configuration

The application uses the following configuration files:
- `application.properties` - Main configuration file
- `application-*.properties` - Environment-specific configurations
- `application-*.yml` - YAML-based configurations

## 📦 Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Security
- Spring Boot Starter Mail
- Spring Boot Starter Validation
- Spring Boot DevTools
- MySQL Connector
- Log4j2

## 🏗️ Project Structure

```
my-spring-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── target/
├── pom.xml
└── README.md
```

## 🔐 Security

The application uses Spring Security for authentication and authorization. Make sure to configure your security settings in the appropriate configuration files.

## 📧 Email Configuration

Spring Mail is configured for sending emails. Update the mail properties in your configuration files to use your email service.

## 📝 Logging

The application uses Log4j2 for logging. Log files are stored in the `logs` directory.