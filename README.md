# Swalha Administrative System - Backend

This is the Spring Boot backend for the Swalha Administrative Financial & Student Management System.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

## Setup

1. **Database Setup**
   - Create a PostgreSQL database named `swalha_admin`
   - Update database credentials in `src/main/resources/application.properties` if needed

2. **Install Dependencies**
   ```bash
   mvn clean install
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## Default Users

The system comes with pre-configured users for development:

- **Admin**: username: `admin`, password: `admin123`
- **Registrar**: username: `registrar`, password: `registrar123`
- **Accountant**: username: `accountant`, password: `accountant123`

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login

### Users (Admin only)
- `GET /api/admin/users` - Get all users
- `POST /api/admin/users` - Create user
- `PUT /api/admin/users/{id}` - Update user
- `DELETE /api/admin/users/{id}` - Delete user

### Students (Admin, Registrar)
- `GET /api/registrar/students` - Get all students
- `POST /api/registrar/students` - Create student
- `PUT /api/registrar/students/{id}` - Update student
- `DELETE /api/registrar/students/{id}` - Delete student

### Classes (Admin, Registrar)
- `GET /api/registrar/classes` - Get all classes
- `POST /api/registrar/classes` - Create class
- `PUT /api/registrar/classes/{id}` - Update class
- `DELETE /api/registrar/classes/{id}` - Delete class

### Attendance (Admin, Registrar)
- `GET /api/registrar/attendance` - Get attendance records
- `POST /api/registrar/attendance` - Mark attendance
- `GET /api/registrar/attendance/student/{studentId}` - Get student attendance

### Revenues (Admin, Accountant)
- `GET /api/accountant/revenues` - Get all revenues
- `POST /api/accountant/revenues` - Create revenue
- `PUT /api/accountant/revenues/{id}` - Update revenue
- `DELETE /api/accountant/revenues/{id}` - Delete revenue

### Expenses (Admin, Accountant)
- `GET /api/accountant/expenses` - Get all expenses
- `POST /api/accountant/expenses` - Create expense
- `PUT /api/accountant/expenses/{id}` - Update expense
- `DELETE /api/accountant/expenses/{id}` - Delete expense

### Reports (Admin, Accountant)
- `GET /api/accountant/reports/summary` - Get financial summary
- `GET /api/accountant/reports/revenues` - Get revenue reports
- `GET /api/accountant/reports/expenses` - Get expense reports

## Security

- JWT-based authentication
- Role-based access control (ADMIN, REGISTRAR, ACCOUNTANT)
- Password encryption using BCrypt

## Database

The system uses JPA/Hibernate with automatic schema generation. Tables will be created automatically on first run.

## Development

- Enable debug logging in `application.properties`
- Sample data is automatically seeded on first run
- CORS is enabled for `http://localhost:5173` (frontend) 