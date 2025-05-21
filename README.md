# Clothing Store E-Commerce Platform

A full-featured e-commerce platform for clothing retail developed with Java Spring Boot and React.

## Table of Contents
- [Overview](#overview)
- [Database Structure](#database-structure)
- [Technology Stack](#technology-stack)
- [Features](#features)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [License](#license)

## Overview

This application is a complete e-commerce solution for clothing retailers, providing inventory management, customer relationship management, order processing, and an online storefront. It supports multi-store operations with robust stock management and employee role-based access control.

## Database Structure

The system utilizes PostgreSQL with the following data model:

### Core Entities

#### Store Management
- **Store**: Central entity for physical locations
- **Storage**: Inventory storage locations within stores
- **Employee**: Staff members with role-based permissions
- **Supplier**: Product vendors and their contact information

#### Product Management
- **Product**: Clothing items with size, color, and pricing details
- **Categories**: Product classification system
- **Store_Inventory**: Current stock levels by store
- **Reserved_Stock**: Items temporarily held for customers
- **Image**: Product images

#### Customer Management
- **Customer**: Core customer information
- **Users**: Authentication and security details
- **Sessions**: User session management
- **Addresses**: Customer shipping and billing addresses
- **Phone_Numbers_Customers**: Customer contact info

#### Order Processing
- **Customer_Order**: Order header information
- **Order_Details**: Line items in each order
- **Payment_Methods**: Available payment options
- **Return**: Product return tracking
- **Shipping**: Delivery tracking information

#### Marketing & Engagement
- **Promotions**: Discount codes and special offers
- **Cart**: Shopping cart functionality
- **Wishlist**: Saved items for future purchase
- **Reviews**: Customer product ratings and feedback
- **Notification**: Customer and employee alerts

### Entity Relationship Diagram

The database contains 30 tables with appropriate foreign key relationships and constraints to maintain data integrity.

## Technology Stack

### Backend
- **Language**: Java 17
- **Framework**: Spring Boot 3.x
- **ORM**: Hibernate with JPA
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT authentication
- **Documentation**: Swagger/OpenAPI

### Frontend
- **Framework**: React 18
- **Routing**: React Router 6
- **HTTP Client**: Axios
- **UI Framework**: Bootstrap 5
- **State Management**: React Hooks

## Features

- Multi-store inventory management
- Role-based access control (Admin, Manager, Seller, Stockkeeper)
- Product catalog with categories, images, and detailed descriptions
- Customer account management
- Shopping cart and wishlist functionality
- Order processing and tracking
- Returns management
- Promotion and discount system
- Review and rating system
- Audit logging for security and compliance

## Installation

### Prerequisites
- Java 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6+
- Node.js 14+ and npm 6+

### Backend Setup

1. Clone the repository:
```bash
git clone https://github.com/your-username/clothing-store.git
cd clothing-store
```

2. Configure database connection in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/clothing_store_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Create the PostgreSQL database:
```bash
createdb clothing_store_db
```

4. Build the backend application:
```bash
mvn clean install
```

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

## Running the Application

### Running the Backend

Start the Spring Boot application:

```bash
# From the root directory
mvn spring-boot:run
```

The backend API will be available at http://localhost:8080/api

### Running the Frontend

Start the React development server:

```bash
# From the frontend directory
npm start
```

The frontend application will be available at http://localhost:3000

## API Documentation

API documentation is available via Swagger UI at http://localhost:8080/swagger-ui.html after starting the backend application.

## License

[MIT License](LICENSE)
