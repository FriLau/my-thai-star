# bookingmanagement-spring Project

This project uses Spring Boot, a Java Framework and Spring Native.

## Running the application in dev mode

You can run your application using:
```shell script
./mvnw spring-boot:run
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/bookingmanagement-spring-0.0.1-SNAPSHOT.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/bookingmanagement-spring-1.0.0-SNAPSHOT-runner`

## Docker Images

To create docker images from this project, you first need to package the project:

```shell script
./mvnw package 
```

Now you can create the Docker Image using:

```shell script
docker build -f src/main/docker/Dockerfile.jvm -t springio/bookingmanagement-spring-jvm .
```

After you created the Docker Image, you can run it in a container using:

```shell script
docker run -i --rm -p 8080:8080 springio/bookingmanagement-spring-jvm
```