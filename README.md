# README

## Building
The project uses gradle, and it may be compiled using the `gradle build` command from the root directory.
This will also run all the tests defined in the project.

## Running
Since the project is a Spring Boot application, use the gradle bootRun plugin may to initialize a local server for testing.
Executing `gradle bootRun` from the root directory will achieve this.
A tool such as Postman may also be helpful to send some test commands to `localhost:8080`, where the local instance run by default. 