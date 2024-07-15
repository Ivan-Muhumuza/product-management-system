
# E-Commerce Product Management System

[Loom video link here](https://www.loom.com/share/24eb78d900274a8291ad1be379477cc6?sid=b4cf6d4b-1603-475f-a7ec-0f5716848d05)

## Overview
The E-Commerce Product Management System is designed to manage categories, products, reviews, and support tickets. The application utilizes a combination of relational and NoSQL databases, providing flexibility in data storage and retrieval.

## Key Components

### 1. **Architecture**
- **Spring Boot**: The project is built on Spring Boot for ease of setup and rapid development.
- **Data Storage**:
    - **Relational Database**: For core entities like categories and products.
    - **MongoDB**: For NoSQL data storage, specifically for products.

### 2. **Core Entities**
- **Category**: Represents product categories and is managed in a binary tree structure for efficient searching.
- **Product**: Represents products, stored in both relational and NoSQL databases, with additional features like images, stock levels, and related products.
- **Review**: Allows users to submit reviews for products, including ratings and comments.
- **Support Ticket**: Tracks customer issues related to products.

### 3. **Services**
- **CategoryService**: Handles CRUD operations for categories, integrating with both the database and binary tree.
- **ProductService**: Manages product-related operations, including inventory management and product retrieval.
- **NoSQLProductService**: Interfaces with MongoDB for additional product information storage.
- **ReviewService**: Manages product reviews.
- **SupportTicketService**: Handles customer support ticket operations.

### 4. **Controllers**
- **CategoryController**: Exposes endpoints for managing categories.
- **NoSQLProductController**: Provides endpoints for NoSQL product operations.
- **ProductController**: Handles product-related HTTP requests.
- **ReviewController**: Manages product reviews through RESTful endpoints.
- **SupportTicketController**: Handles support ticket operations.

### 5. **Exception Handling**
- **GlobalExceptionHandler**: Centralized exception handling for resource not found and general exceptions.

### 6. **Interceptors**
- **LoggingInterceptor**: Logs incoming requests and responses for better traceability.

## Setup Instructions

### Prerequisites
- Java 21+
- Maven
- MongoDB instance running

### Project Setup
1. **Clone the Repository**
   ```bash
   git clone https://github.com/Ivan-Muhumuza/product-management-system.git
   ```

2. **Configure Database**
    - Update `application.properties` with your database configurations:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/yourdb
      spring.datasource.username=yourusername
      spring.datasource.password=yourpassword
      spring.data.mongodb.uri=mongodb+srv://<username>:<password>abc.def.mongodb.net/yourdb
      ```

3. **Build the Project**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**
    - The application will be accessible at `http://localhost:8080`.

## API Endpoints

### Product Endpoints
- `GET /products`
- `GET /products/page`
- `GET /products/{id}`
- `POST /products`
- `PUT /products/{id}`
- `DELETE /products/{id}`
- `GET /products/category/{categoryName}`
- `GET /products/name/{name}`
- `GET /products/expensive/{price}`

### Category Endpoints
- `GET /categories`
- `GET /categories/{id}`
- `POST /categories`
- `PUT /categories/{id}`
- `DELETE /categories/{id}`
- `GET /categories/page`
- `GET /categories/name/{name}`
- `GET /categories/with-products`

### Review Endpoints
- `POST /reviews`
- `GET /reviews`

### Support Ticket Endpoints
- `POST /support-tickets`
- `GET /support-tickets`

### NoSQL Product Endpoints
- `POST /nosql/products`
- `GET /nosql/products` 
