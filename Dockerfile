# Multi-stage Dockerfile for building and running the Spring Boot application
# Build stage: use a JDK image to run the Gradle wrapper and produce the fat jar
# Stage 1: Build the Spring Boot app with Gradle
FROM gradle:8.5-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN chmod +x gradlew && ./gradlew -x test bootJar --no-daemon

# Stage 2: Run the app in a slim JRE image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Optional JVM tuning
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Copy the fat JAR from the build stage
COPY --from=build /workspace/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar


