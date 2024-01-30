# Project Name

## Prerequisites

- Java 21
- Docker
- Docker Compose
- Maven

## Setup

1. Clone the repository:

```bash
git clone https://github.com/JacentyI1/project-name.git
cd project-name
```

2. Build the project:

```bash
mvn clean install
```

3. Start the database:

```bash
docker-compose up
```

## Running the Application

After setting up the database, you can run the application using the following command:

```bash
mvn spring-boot:run
```

The application will be accessible at `http://localhost:8080`.

## Testing

To run the tests, use the following command:

```bash
mvn test
```

## Stopping the Application

To stop the application and the database, use the following commands:

```bash
# Stop the Spring Boot application
Ctrl+C

# Stop the Docker Compose services
docker-compose down
```
```

Please note that you might need to adjust the commands according to your project's specific setup. For example, the application's port might be different, or there might be additional services that need to be started with Docker Compose.

