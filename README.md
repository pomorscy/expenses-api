# Expenses API

## Synopsis

Expenses API is a simple RESTfull web service for managing your daily expenses.

## Motivation

The purpose of this project is to gather sample usage of popular frameworks, libraries, testing, documentation and integration tools. 

## Installation

In order to run application in your environment, you need to install MySQL Server, maven and java 8.
Once it is running, you can create a schema and a default user using a sql script:
```
mysql -u root -p < src/main/resources/db/create.sql
```

Flyway will create required tables for you on application startup. 

You run the Service using maven command. 

```
mvn spring-boot:run
```

## API Reference

Service is described using Spring Docs project. 
Current documentation can be found [here](http://htmlpreview.github.io/?https://github.com/pomorscy/expenses-api/blob/master/documentation.html).

## Tests

In order to run unit test use maven command:

```
mvn clean package
```

To run the integration tests as well use:
```
mvn clean verify
```