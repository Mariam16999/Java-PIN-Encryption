# Use an official openjdk image as the base
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/spring-encryption-0.0.1-SNAPSHOT.jar /app/spring-encryption.jar

# Expose the port the app will run on
EXPOSE 8081

# Command to run the app
ENTRYPOINT ["java", "-jar", "spring-encryption.jar"]
