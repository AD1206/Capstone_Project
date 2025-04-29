# ===== Stage 1: Build the application =====
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven configuration and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and package the application (skipping tests)
COPY src ./src
RUN mvn clean package -DskipTests

# ===== Stage 2: Create the runtime image =====
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/skillbridge-0.0.1-SNAPSHOT.jar .

# Expose the application port
EXPOSE 8080

# Define the entry point
ENTRYPOINT ["java", "-jar", "/app/skillbridge-0.0.1-SNAPSHOT.jar"]
