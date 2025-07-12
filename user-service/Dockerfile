# Stage 1: Build the Spring Boot app using Maven and Java 21
FROM eclipse-temurin:21 AS build
WORKDIR /app

# Copy Maven wrapper and necessary files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Make the Maven wrapper executable
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

COPY src src

# Build the project without running tests
RUN ./mvnw clean package -DskipTests

# ---------------------------------------------------------

# Stage 2: Create minimal and secure runtime image
FROM eclipse-temurin:21-jre as runtime

# Create non-root user for better security
RUN useradd -ms /bin/bash springuser
USER springuser

WORKDIR /app

# Copy only the built jar from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port (Spring Boot default)
EXPOSE 8080

# Optional: Health check (for Docker or k8s)
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
