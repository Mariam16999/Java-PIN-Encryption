# Start with a base image containing OpenJDK
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Install Maven dependencies and package the application
RUN apt-get update && apt-get install -y maven \
    && mvn clean package -DskipTests \
    && mv target/*.jar app.jar

# Expose the new application port
EXPOSE 8081

# Define the entry point
ENTRYPOINT ["java", "-jar", "app.jar"]
