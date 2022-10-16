# bookingmanagement-quarkus Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

This project uses H2 and PostgeSQL as a database. To build a native image, PostgreSQl is needed.
To change the database, use the right config in the resources/application.properties.

## Install the project

To install the project, use:
```shell script
./mvnw clean install
```

## Using the PostgreSQL database

You need to run a PostgreSQL database to use PostgreSQL and run native images.

First you need to get a postgres docker image:
```shell script
docker pull postgres:11
```

You can run the PostgreSQL server using:
```shell script
docker run --name dev-postgres -p 5432:5432 -e POSTGRES_PASSWORD=demo -d postgres:11
docker exec dev-postgres psql -U postgres -c"CREATE DATABASE bookingmanagment" postgres
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Docker Images

To create docker images from this project, you first need to package the project:

```shell script
./mvnw package 
```

Now you can create the Docker Image using:
```shell script
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/bookingmanagement-quarkus-jvm .
```

After you created the Docker Image, you can run it in a container using:

```shell script
docker run -i --rm -p 8080:8080 quarkus/bookingmanagement-quarkus-jvm
```

## Creating a native executable

You can create a native executable using:
```shell script
./mvnw clean -Pnative -DskipTests package
```

You can then execute your native executable with: `./target/bookingmanagement-quarkus-1.0.0-SNAPSHOT-runner`
