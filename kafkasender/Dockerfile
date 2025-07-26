# Stage 1: Build Spring Boot app with Maven
# Use Maven with Java 17 for building
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app
# Copy all project files to the container
COPY . .
# Build the application and skip tests for faster build
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app with a lightweight JRE
# Use a small Java runtime for running the app
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar
# Expose the default Spring Boot port
EXPOSE 8080
# Run the Spring Boot app
CMD ["java", "-jar", "app.jar"] 