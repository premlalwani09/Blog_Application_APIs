# Blog Application APIs

## Overview
The **Blog Application APIs** is a RESTful service built using Spring Boot. It provides CRUD operations for users, blog posts, categories, and comments. The application also includes authentication and authorization using JWT tokens.

## Technologies Used
- **Spring Boot** - Framework for building RESTful services
- **Spring Security** - Authentication and authorization using JWT
- **Spring Data JPA** - For database interactions
- **Hibernate** - ORM framework
- **MySQL** - Database for storing application data
- **Swagger** - API documentation
- **Lombok** - To reduce boilerplate code
- **Maven** - Dependency management

---

## Features
- User authentication (Signup, Login, JWT-based authentication)
- CRUD operations for Posts, Categories, and Comments
- Pagination and Sorting
- Exception handling
- API documentation using Swagger

---

## API Endpoints

### User Controller
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/v1/auth/register` | POST | Register a new user |
| `/api/v1/auth/login` | POST | Authenticate user and get JWT |

### Post Controller
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/v1/posts` | POST | Create a new post |
| `/api/v1/posts/{postId}` | GET | Get post by ID |
| `/api/v1/posts` | GET | Get all posts with pagination |
| `/api/v1/posts/{postId}` | PUT | Update post |
| `/api/v1/posts/{postId}` | DELETE | Delete post |

### Category Controller
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/v1/categories` | POST | Create a new category |
| `/api/v1/categories/{categoryId}` | GET | Get category by ID |
| `/api/v1/categories` | GET | Get all categories |
| `/api/v1/categories/{categoryId}` | PUT | Update category |
| `/api/v1/categories/{categoryId}` | DELETE | Delete category |

### Comment Controller
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/v1/comments` | POST | Add a comment to a post |
| `/api/v1/comments/{commentId}` | DELETE | Delete comment |

---

## Configuration
Ensure you have the following properties set in `application.properties`:
```properties
spring.application.name=Blogging-Application-API

server.port=9090

#db condiguration
spring.datasource.url=jdbc:mysql://localhost:3306/blog_app_apis
spring.datasource.username=root
spring.datasource.password=root

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# create , update , create-drop , validate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# for Image Purpose
spring.servlet.multipart.max-file-size = 10MB
spring.servlet.multipart.max-request-size = 10MB
project.image = images/

logging.level.org.springframework.security=DEBUG
spring.main.allow-circular-references=true
```

---

## Running the Application
1. Clone the repository:
   ```sh
   git clone https://github.com/premlalwani09/Blog_Application_APIs.git
   ```
2. Navigate to the project directory:
   ```sh
   cd Blog_Application_APIs
   ```
3. Build the project:
   ```sh
   mvn clean install
   ```
4. Run the application:
   ```sh
   mvn spring-boot:run
   ```
5. Access API documentation at:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

