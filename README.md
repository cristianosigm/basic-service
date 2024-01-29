# Basic Web Service Back-end

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

A basic microservice, which demonstrates the usage of JWT as authentication, and JPA as data persistence.

Current Version: 0.0.1 alpha.

## Requirements

For building and running the application you need:

- [JDK 17](https://openjdk.org/projects/jdk/17/);
- [Gradle 4.4](https://docs.gradle.org/4.4.1/release-notes.html) or higher.

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. 
One way is to execute the `main` method in the `com.cs.sigm.SigmApplication` class from your IDE.

Alternatively you can use the command below, assuming you already have your gradle installation working:

```shell
./gradlew bootRun
```

# Known issues
* The current version of the system does not persist the blacklisted tokens, 
so whenever the server is restarted, all tokens that were not yet expired
will continue valid.
* Token expiration exceptions cannot be handled using GlobalExceptionHandler.