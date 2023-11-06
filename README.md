# Java Spring Login/Register/Recover Password Screen

## Overview
This project uses Java 17 and the Spring framework. 
Encryption of the password is handled with BCrypt salted hashing. 
Usernames and passwords are stored in the database.db file using SQLite JDBC library.
Thymeleaf is used for UI interactions between the backend and the frontend.

## Class Descriptions
DemoApplication.java implements the SpringBootApplication. GET and POST requests are mapped to pages form, success and fail pages via Thymeleaf.
Database.java handles the actions for manipulating the database.db file.
User.java contains the username and password information of the user.

# Instructions
- Update maven dependencies
- Compile and run DemoApplication
- Access the Home page from http://localhost:8080/
