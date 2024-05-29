FROM openjdk:21-jdk

# Working directory
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY target/training-duration-microservice-0.0.1-SNAPSHOT.jar /app/training-duration-microservice-0.0.1-SNAPSHOT.jar

# Make port 9093 available to the world outside this container
EXPOSE 9093

# Run the application
ENTRYPOINT ["java", "-jar", "training-duration-microservice-0.0.1-SNAPSHOT.jar"]