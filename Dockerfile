# Use official OpenJDK 21 runtime as base
FROM eclipse-temurin:21-jdk-jammy

# Set working directory inside container
WORKDIR /app

# Copy the Spring Boot fat JAR to the container
COPY target/*.jar app.jar

# Expose port (adjust if your app runs on a different port)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
