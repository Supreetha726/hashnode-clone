# Hashnode Clone – Developer Blogging & Publishing Platform

A full-stack developer blogging and publishing platform inspired by Hashnode.

The application allows users to register, log in securely, create and manage blog posts, add technology tags, search posts, manage their profiles, and publish or save posts as drafts.

## Project Overview

This project is developed as a full-stack web application using:

- React.js for the frontend
- Java Spring Boot for the backend
- Spring Security and JWT for authentication
- MySQL for database management
- JPA/Hibernate for database interaction
- Maven for backend dependency management

## Technology Stack

### Frontend

- React.js
- Vite
- JavaScript
- Axios
- React Router
- Context API
- Markdown

### Backend

- Java 17
- Spring Boot 3.3.5
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- Maven

### Database

- MySQL

## Project Structure

```text
Hashnode-Clone/
│
├── frontend/
│   ├── public/
│   ├── src/
│   ├── package.json
│   ├── package-lock.json
│   ├── index.html
│   └── vite.config.js
│
├── backend/
│   ├── src/
│   └── pom.xml
│
└── README.md
```

## Features

### User Authentication

- User registration
- User login
- Password encryption using BCrypt
- JWT-based authentication
- Protected routes
- Authentication persistence
- Logout functionality

### Blog Posts

- Create blog posts
- Edit blog posts
- Delete blog posts
- Save posts as drafts
- Publish posts
- Generate unique post slugs
- Markdown-based content
- Post excerpts
- Cover images
- Author information
- Created and updated timestamps

### Tags

- Add technology tags to posts
- Create tags
- View available tags
- Filter posts by tags
- Display tags associated with posts

### Search and Feed

- Public post feed
- Search posts by title
- Filter posts by tags
- View individual published posts

### User Profiles

- View developer profiles
- View user's published posts
- Edit profile information
- Display user information and posts

### Security

- JWT authentication
- BCrypt password hashing
- Protected backend endpoints
- Author ownership validation
- CORS configuration
- Password excluded from API responses

## Prerequisites

Install the following before running the project:

- Java 17 or higher
- Node.js
- npm
- MySQL
- Maven

The backend can also be imported and run as a Maven project using Eclipse or IntelliJ IDEA.

## Database Setup

### 1. Start MySQL

Make sure your MySQL server is running.

### 2. Create the Database

Open MySQL and execute:

```sql
CREATE DATABASE hashnode_clone;
```

### 3. Configure Database Credentials

Open:

```text
backend/src/main/resources/application.properties
```

Update the following properties according to your local MySQL configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hashnode_clone
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

The application uses JPA/Hibernate to create and update the required tables automatically.

## Running the Backend

### Option 1 – Using Eclipse

1. Open Eclipse.
2. Select **File → Import**.
3. Select **Maven → Existing Maven Projects**.
4. Select the `backend` folder.
5. Click **Finish**.
6. Wait for Maven dependencies to download.
7. Open:

```text
backend/src/main/java/com/hashnodeclone/HashnodeCloneApplication.java
```

8. Right-click the file.
9. Select **Run As → Spring Boot App**.

The backend will run on:

```text
http://localhost:8081
```

### Option 2 – Using Maven

Open a terminal inside the backend folder:

```bash
cd backend
```

Run:

```bash
mvn spring-boot:run
```

The backend will start on:

```text
http://localhost:8081
```

## Running the Frontend

Open another terminal.

Navigate to the frontend folder:

```bash
cd frontend
```

Install the dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

The frontend will normally run at:

```text
http://localhost:5173
```

Open the displayed URL in a web browser.

## Application URLs

### Frontend

```text
http://localhost:5173
```

### Backend

```text
http://localhost:8081
```

## REST API Endpoints

### Authentication

```text
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/me
```

### Posts

```text
GET    /api/posts
GET    /api/posts/{slug}
GET    /api/posts/mine
POST   /api/posts
PUT    /api/posts/{id}
DELETE /api/posts/{id}
```

### Tags

```text
GET /api/tags
GET /api/tags/{name}
```

### Users

```text
GET /api/users/{id}
PUT /api/users/me
```

## Application Flow

```text
User
  |
  v
React Frontend
  |
  | HTTP Requests using Axios
  v
Spring Boot REST API
  |
  v
Spring Security + JWT
  |
  v
Service Layer
  |
  v
JPA / Hibernate
  |
  v
MySQL Database
```

## Authentication Flow

```text
User Registration
       |
       v
Password encrypted using BCrypt
       |
       v
User stored in MySQL
       |
       v
User Login
       |
       v
JWT Token Generated
       |
       v
Token stored in Frontend
       |
       v
Token sent with protected API requests
       |
       v
Spring Security validates JWT
       |
       v
Protected Resource Access
```

## Post Management Flow

```text
User Login
    |
    v
Create Post
    |
    +----> Save as Draft
    |
    +----> Publish
    |
    v
Post stored in MySQL
    |
    v
Public Feed
    |
    +----> Search
    |
    +----> Filter by Tags
    |
    v
View Published Post
```

## Security

The application uses:

- Spring Security
- JWT authentication
- BCrypt password encryption
- Protected API endpoints
- Author ownership validation
- CORS configuration

Passwords are not returned in user API responses.

Do not upload real database passwords or private JWT secrets.

Before running the project, update the sensitive values in:

```text
backend/src/main/resources/application.properties
```

For example:

```properties
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
jwt.secret=YOUR_SECRET_KEY
```

## Troubleshooting

### Backend does not start

Make sure:

- Java 17 is installed
- MySQL is running
- The `hashnode_clone` database exists
- MySQL username and password are correct
- Maven dependencies have been downloaded

### Frontend does not start

Run:

```bash
npm install
```

Then:

```bash
npm run dev
```

### Database connection error

Check:

```text
backend/src/main/resources/application.properties
```

Verify the MySQL username, password, database name, and port.

## Future Enhancements

Possible future improvements include:

- Likes
- Comments
- Following users
- Bookmarks
- Pagination
- Reading-time calculation
- Image upload
- Dark mode
- Additional profile customization

## Project Status

The core application features have been implemented and tested locally, including:

- User authentication
- JWT security
- Post creation and management
- Draft and published posts
- Tags
- Search
- Developer profiles
- Profile editing
- Protected routes
- MySQL database integration

## Author

**Supreetha N S**

Hashnode Clone – Developer Blogging & Publishing Platform