# Raydana_Api_Notes
Build Restful CRUD API for a simple Note-Taking application using Spring Boot, Mysql, JPA and Hibernate.

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Mysql - 5.x.x

## Steps to Setup
**1. Clone the application**

```bash
git clone https://github.com/raydana/raydana_api_notes.git
```
**2. Create Mysql database**
```bash
create database new_db
```

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.url` to ypur mysql engine server IP

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Run the app using maven**
```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines CRUD APIs.

You can view or test them using this link  <http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config>.

## ETL Job
In this app, an xml file of users and notes is created alternately and every hour and saved in Drive;
you can config xml directory path.
first open `src/main/resources/application.properties`
and then change value of the key 'dataTransform.export.directoryPath'.

## Raydana Company website

<https://www.raydana.com/>

