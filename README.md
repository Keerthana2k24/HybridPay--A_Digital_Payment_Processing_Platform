# HybridPay — Hybrid Payment System

A full-stack payment platform built with Spring Boot + MySQL + HTML/CSS/JS.

---

## 🚀 How to Run in IntelliJ

### Step 1 — MySQL Setup
Open MySQL Workbench or command line and run:
```sql
CREATE DATABASE hybridpay;
```

### Step 2 — Configure Password
Open `src/main/resources/application.properties`
Change this line to your MySQL password:
```
spring.datasource.password=YOUR_PASSWORD_HERE
```

### Step 3 — Open in IntelliJ
- File → Open → Select the `HybridPay` folder
- IntelliJ will auto-detect it as a Maven project
- Wait for Maven to download dependencies (first time takes ~2 mins)

### Step 4 — Run
- Open `HybridPayApplication.java`
- Click the green ▶ Run button
- Wait for: `Started HybridPayApplication in X seconds`

### Step 5 — Open Browser
Go to: **http://localhost:8080**

---

## 📁 Project Structure

```
HybridPay/
├── src/main/java/com/hybridpay/
│   ├── HybridPayApplication.java      ← Entry point
│   ├── entity/
│   │   ├── User.java                  ← Users table
│   │   └── Transaction.java           ← Transactions table
│   ├── repository/
│   │   ├── UserRepository.java        ← DB queries for users
│   │   └── TransactionRepository.java ← DB queries for transactions
│   ├── service/
│   │   ├── UserService.java           ← Business logic: auth, UPI, wallet
│   │   └── TransactionService.java    ← Business logic: payments
│   └── controller/
│       ├── AuthController.java        ← /api/auth/*
│       ├── UserController.java        ← /api/user/*
│       └── TransactionController.java ← /api/txn/*
│
└── src/main/resources/
    ├── application.properties         ← DB config
    └── static/
        ├── index.html                 ← Main page
        ├── css/style.css              ← All styles
        └── js/app.js                  ← All frontend logic
```

---

## 🔌 API Endpoints

### Auth
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login |

### User
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/user/{id}/dashboard` | Balance + stats |
| POST | `/api/user/{id}/link-upi` | Link UPI ID |
| POST | `/api/user/{id}/link-wallet` | Link MetaMask address |

### Transactions
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/txn/{id}/transfer` | Send money by email |
| POST | `/api/txn/{id}/upi` | UPI transfer |
| POST | `/api/txn/{id}/deposit` | Add money |
| POST | `/api/txn/{id}/crypto` | Log MetaMask tx |
| GET | `/api/txn/{id}/history` | Transaction history |

---

## 💡 Innovation Points (for internship Round 2)

1. **Hybrid Payment Architecture** — One backend handles 4 payment modes (Wallet, UPI, Deposit, Crypto) with a single unified transaction table. Most payment apps handle one mode only.

2. **Atomic Transactions** — `@Transactional` on transfer service ensures if deduction succeeds but credit fails, both operations rollback. Zero money loss guaranteed.

3. **MetaMask Integration** — Web3 wallet connected directly to a traditional banking system — bridging DeFi and traditional finance (hence "Hybrid").

4. **Universal Transaction Audit Trail** — Every payment across all modes is stored in one table with type, mode, status, note, and timestamp. Full forensic audit capability.

5. **Real-time Balance Computation** — Balance updates immediately on every transaction without requiring a separate balance table.

---

## 🎁 Test It Quickly

1. Register two accounts (User A and User B)
2. User A gets ₹10,000 welcome bonus
3. Log in as User A → Send Money → enter User B's email → send ₹500
4. Log out → Log in as User B → see ₹500 received
5. Both users see the transaction in History
