# 🛒 Online Shopping Application

A full-stack Java and Angular application simulating an online shopping platform called **Super Duper Mart™**. The application supports user registration/login with JWT authentication, product browsing, shopping cart, order management, watchlist, and admin (seller) dashboards with sales analytics.

---

## ✨ Features

### 👤 Customer (User) Features
- ✅ User registration and secure login with JWT tokens
- 🛍️ Browse all in-stock products with detailed views
- 🛒 Add multiple products to a shopping cart (stored locally)
- 🛒 Place orders with quantity validation and stock management
- 🛑 Cancel orders before completion with automatic stock restoration
- ⭐ Manage a watchlist of favorite products
- 📊 View personal order history, order details, and recently purchased items

### 👨‍💼 Admin (Seller) Features
- 🏠 Dashboard with paginated order overview and user info
- 📦 Product management: add, edit, and view product details including prices and stock
- 🔄 Manage order status: processing, completed, or canceled, with stock adjustment
- 📈 Sales analytics: view most popular and most profitable products, total items sold
- ⚙️ Secure backend using Spring Security, JWT, and validation

---

## 🧰 Technology Stack

- **Backend:** Java, Spring Boot, Hibernate (HQL & Criteria API), Spring Security with JWT, Spring AOP, Spring Validation, MySQL
- **Frontend:** Angular 14, Angular Material UI library, Local Storage for shopping cart
- **Tools:** Postman for API testing, Maven, Git/GitHub

---

## 🧱 Architecture

The system follows a **layered architecture**:

- **Backend:**
  - Controller layer: REST endpoints via `@RestController`
  - Service layer: Business logic and transaction management
  - DAO layer: Hibernate data access using HQL and Criteria API
  - Security layer: JWT-based authentication and authorization
- **Frontend:**
  - Angular components for user/admin pages, forms, and UI
  - Angular Guards for route protection based on user roles
  - Services for API communication and local storage management

---

## 📄 Pages Overview

### 👨‍🎓 Customer/User

| Page                 | Description                                               |
|----------------------|-----------------------------------------------------------|
| Login/Register       | Secure login and new user registration forms              |
| Home Page            | Browse available products (exclude out-of-stock)          |
| Product Detail Page  | View product description and retail price                  |
| Shopping Cart        | Manage items before purchase, stored in local storage      |
| Order Placement      | Place multi-item orders with stock checks                  |
| Order History        | View all past orders with details and cancel if eligible   |
| Watchlist            | Add/remove favorite products and view in-stock watchlist   |

### 👨‍💼 Admin (Seller)

| Page                     | Description                                               |
|--------------------------|-----------------------------------------------------------|
| Admin Dashboard          | View paginated order list with user and status info       |
| Product Management       | Add new products, edit prices, stock, and descriptions    |
| Order Management         | Change order statuses: processing, completed, canceled    |
| Sales Analytics          | View most popular and profitable products, total sold     |

---

## 📊 Database Schema

The MySQL database includes the following main tables:

- `user` — stores user credentials and roles
- `product` — product catalog with descriptions, prices, stock quantity
- `order` — user orders with status and timestamps
- `order_item` — items per order with quantities and prices
- `watchlist` — user watchlist entries

---

## 🧾 Entity Relationship Diagram (ERD)

![image](![image](https://github.com/user-attachments/assets/1a709a74-6ff7-48e3-9b29-02aef7f0bbc1))

---

## 📷 Screenshots

### 📌 Register Page  
![image](https://github.com/user-attachments/assets/56987a8c-aab6-4d20-8d27-48fff6388613)

### 📌 User Home Page  
![image](https://github.com/user-attachments/assets/1f4cbdc7-94e8-49b5-980b-32bdf7327c75)

### 📌 Products Page  
![image](https://github.com/user-attachments/assets/6b3bc29d-2519-47ba-a413-5b44a86cf6e5)

### 📌 Product Details Page
![image](https://github.com/user-attachments/assets/25800be6-0377-41e4-932a-362f03e7e318)

### 📌 Cart Page
![image](https://github.com/user-attachments/assets/251c2922-65a6-47e0-97ba-ae85f628ab3a)

### 📌 Watchlist Page
![image](https://github.com/user-attachments/assets/05d0783d-4a73-40da-bfe7-01176ac13afd)


### 📌 Admin Home Page  
![image](https://github.com/user-attachments/assets/e11e2f36-2c22-4f11-b60a-f9b70a62b1dd)
![image](https://github.com/user-attachments/assets/b2f4ba01-bbf7-4fc5-9edf-ddfcc769c539)

### 📌 Admin User Management  
![image](https://github.com/user-attachments/assets/42120b8b-27de-4481-bd57-3654b6fae871)

### 📌 Admin Product Management  
![image](https://github.com/user-attachments/assets/a1553ead-a73d-4ce7-8e0b-899d126d4fd5)

---

## 🚀 Getting Started

### Prerequisites

- Java 8
- Maven 3+
- Spring Boot 2.7
- Node.js and npm
- Angular CLI 14
- MySQL database setup with provided schema

---

### Setup Instructions

1. Clone the repository:

```bash
git clone https://github.com/AbhishekyPhalak/Online-Shopping-Application.git
cd Online-Shopping-Application
