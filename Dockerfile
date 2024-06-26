# Stage 1: Build the application
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# Copy the service account key file
COPY service-account-key.json .
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/service-account-key.json
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]