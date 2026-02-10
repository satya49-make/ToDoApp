# Multi-stage Dockerfile for building and running the Spring Boot application
# Build stage: use a JDK image to run the Gradle wrapper and produce the fat jar
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /workspace
# Copy everything and run the build (skip tests to speed up image builds by default)
COPY . .
RUN ./gradlew -x test bootJar --no-daemon

# Runtime stage: smaller JRE image
FROM eclipse-temurin:17-jre-jammy
ARG JAVA_OPTS="-Xms256m -Xmx512m"
ENV JAVA_OPTS=${JAVA_OPTS}
VOLUME /tmp
COPY --from=build /workspace/build/libs/*.jar /app/app.jar
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
EXPOSE 8080

