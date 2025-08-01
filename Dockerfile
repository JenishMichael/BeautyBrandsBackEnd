# Stage 1: Build the jar using Maven and JDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set working directory inside container
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application, skipping tests to speed up build
RUN mvn clean package

# Stage 2: Run the jar using minimal JDK 21 image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /app/target/BeautyBrandsBE-0.0.1-SNAPSHOT.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
