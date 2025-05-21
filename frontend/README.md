# Clothing Store Frontend

This is the frontend application for the Clothing Store e-commerce platform. It's built with React and communicates with the Spring Boot backend API.

## Features

- Browse products by category, brand, color, size, and price
- Search for products
- View product details and reviews
- Add products to cart
- Manage cart items
- User authentication (login/register)
- Responsive design for mobile and desktop

## Prerequisites

- Node.js (v14.x or higher)
- npm (v6.x or higher)

## Installation

1. Clone the repository
2. Navigate to the frontend directory:
```bash
cd frontend
```

3. Install dependencies:
```bash
npm install
```

## Running the Application

To start the development server:

```bash
npm start
```

This will start the React development server on port 3000. The application will be available at http://localhost:3000.

The frontend is configured to proxy API requests to the backend server running on http://localhost:8080.

## Building for Production

To create a production build:

```bash
npm run build
```

This will create a `build` directory with optimized production files that can be served by any static file server.

## Project Structure

- `src/components/`: Reusable UI components
- `src/pages/`: Page components for different routes
- `src/services/`: API service functions
- `src/App.js`: Main application component with routing
- `src/index.js`: Application entry point

## Backend Integration

The frontend is designed to work with the Spring Boot backend API. Make sure the backend server is running on port 8080 before using the frontend application.

## Technologies Used

- React
- React Router
- Axios
- Bootstrap 5 