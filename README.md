ToDo App

A Reactive Spring Boot ToDo Application with JWT-based authentication, role-based access control (RBAC), and MongoDB persistence. This app allows users to register, login, manage their own tasks, and provides admin-level access to view all users' tasks.

Features

User Registration & Login with JWT authentication

Role-based Access Control (RBAC)

USER: Can create, view, update, and delete their own tasks

ADMIN: Can view all tasks grouped by user

Reactive Spring WebFlux for non-blocking APIs

MongoDB Atlas integration for persistence

Secure JWT Handling with HS256 algorithm and externalized secret key

Task Management

Title, description, status (enum), progress percentage

Automatic timestamps (createdAt, updatedAt)


Endpoints

User Endpoints

GET /tasks → Get all tasks for logged-in user

POST /tasks → Create a new task for logged-in user

PUT /tasks/{id} → Update a task (only owner)

DELETE /tasks/{id} → Delete a task (only owner)

Admin Endpoints

GET /tasks/all → Get all tasks grouped by user (ADMIN only)

Technologies Used

Spring Boot 3 / WebFlux

Spring Security (Reactive)

MongoDB Atlas

JWT (jjwt)

Lombok


Testing:
Use Postman or curl:

POST /auth/register

POST /auth/login

GET /tasks (with JWT)

POST /tasks (with JWT)

GET /tasks/all (ADMIN only)