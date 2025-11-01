# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set environment variables
ENV SERVER_PORT=8080

# Create directory inside container
WORKDIR /app

# Copy the jar file
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]