# HybridPay - A Digital Payment Processing Platform

HybridPay is a full-stack digital payment system inspired by modern fintech platforms like Paytm, PhonePe, and Google Pay. It allows users to securely manage their wallet, send money, track transactions, and simulate real-world payment workflows.

---

## Live Demo

🔗 Demo URL: https://hybridpay-a-digital-payment-processing-4y64.onrender.com

---

## Project Overview

HybridPay is designed to demonstrate:

* Secure user authentication
* Wallet-based transaction system
* Real-time balance updates
* Transaction history tracking
* Responsive UI 
* Full deployment pipeline (Backend + DB + Frontend)

---

## Features

### Authentication

* User Registration
* Login system with validation

### Wallet System

* Add money to wallet
* Send money to another user
* Balance updates instantly

### UPI Simulation

* UPI ID linking and transfers

### Dashboard

* Available balance display
* Total sent amount
* Transaction count
* UPI status

### Transactions

* View transaction history
* Status (Success / Failed)
* Date & time tracking

### Responsive UI

* Mobile-friendly layout
* Smooth UX for small screens
---

## Tech Stack

### Backend

* Java 17+
* Spring Boot
* Spring Data JPA
* REST APIs

### Database

* MySQL (Railway)

### Frontend

* HTML5
* CSS3 (Custom UI + Responsive design)
* JavaScript (Vanilla JS)

### Deployment

* Backend: Render
* Database: Railway

---

##  Project Structure

```
HybridPay/
│
├── src/main/java/com/hybridpay/
│   ├── controller/     # REST Controllers
│   ├── service/        # Business Logic
│   ├── repository/     # Database Layer (JPA)
│   ├── entity/         # DB Models
│   └── HybridPayApplication.java
│
├── src/main/resources/
│   ├── static/         # Frontend (HTML/CSS/JS)
│   └── application.properties
│
├── pom.xml             # Maven Dependencies
├── mvnw / .mvn         # Maven Wrapper
├── Dockerfile          # Deployment configuration
└── README.md
```

---

## ⚙️ Installation & Setup

 - [Installation & Deployment Steps](docs/Installation_Deployment_steps.pdf)

---
### Output Screenshots
![Login](outcome-images/1.png)
![Dashboard](outcome-images/2.png)
![Mobile responsive-1](outcome-images/3.png)
![Mobile responsive-2](outcome-images/4.png)
![Mobile responsive-3](outcome-images/5.png)

---

## Future Enhancements

* JWT-based authentication
* Payment gateway integration
* Real-time notifications
* Transaction analytics dashboard
* Microservices architecture
* Role-based access control

---

## Learning Outcomes

Through this project, I gained experience in:

* Full-stack application development
* REST API design
* Database integration with Spring Boot
* UI/UX responsiveness
* Cloud deployment (Render, Railway)
* Debugging real-world production issues

---

⭐ If you like this project, give it a star!
