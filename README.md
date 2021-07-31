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

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Run the app using maven**
```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following CRUD APIs.

    show all notes by user --> GET /api/notes     notice : put username in request header with key : 'header-username'
    
    create new note --> POST /api/notes      
    notice : sample body   :
    {
    "title" : "first title",
    "note" : "first note",
    "username" : "j.mirzazadeh@gmail.com"
    }
        
    update note --> PUT /api/notes/{noteId}    notice : put username in request header with key : 'header-username' and body is like save service
    
    delete user --> DELETE /api/notes/{noteId}     notice : put username in request header with key : 'header-username' 

You can test them using postman or any other rest client.

## Raydana Company website

<https://www.raydana.com/>

