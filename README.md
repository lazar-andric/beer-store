# Project Name
Beer store

## Table of contents
* [Technologies](#Technologies)
* [Requirements](#requirements)
* [Setup](#setup)
* [Curl commands](#curl-commands)
* [H2 console](#h2-console)

## Technologies
* Java 8
* Spring Boot 2
* H2 database
* Gradle


## Requirements

For building and running the application you need:

- [JDK 8](https://www.oracle.com/pt/java/technologies/javase/javase-jdk8-downloads.html)
- [Gradle 5 (5.6.x only) or Gradle 6 (6.3 or later)](https://gradle.org/install/)

## Setup

After cloning the Git repository, go to project root folder and run following commands:

```$xslt
./gradlew build
```
```$xslt
java -jar build/libs/beer-store-0.0.1-SNAPSHOT.jar
```

## Curl commands examples

- Fetching all beers in database
```$xslt
curl --request GET 'http://localhost:8080/api/v1/beers'
```
- Fetch beer with id 1
```$xslt
curl --request GET 'http://localhost:8080/api/v1/beers/1'
```
- Delete beer with id 1
```$xslt
curl --request DELETE 'http://localhost:8080/api/v1/beers/1'
```
- Fill the database up to 10 beers
```$xslt
curl --request POST 'http://localhost:8080/api/v1/beers'
```

#### H2 console

After running the application locally you can access H2 console here:

- [http://localhost:8080/api/v1/h2-console](http://localhost:8080/api/v1/h2-console)