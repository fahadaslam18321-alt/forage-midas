# 🚀 JPMC Advanced Software Engineering Virtual Experience Program (Forage)

Welcome to the **Midas Core** project repository. This project is part of the **JPMorgan Chase & Co. Advanced Software Engineering Virtual Experience Program** on **Forage**. It simulates a modern financial transaction processing engine built using **Java**, **Spring Boot**, **Apache Kafka**, and **H2 In-Memory Database**.

---

## 📌 Project Architecture Overview

The system consists of a core service (**Midas Core**) that listens to financial transaction events, processes them against a user database, integrates with an external incentive service API, and exposes real-time endpoints for balance querying.

```text
                     +-------------------+
                     | Kafka Transaction |
                     |     Producer      |
                     +---------+---------+
                               |
                               v
                     +---------+---------+
                     |   Kafka Topic     |
                     |  (Transactions)   |
                     +---------+---------+
                               |
                               v
         +----------------------------------------------+
         |                  MIDAS CORE                  |
         |----------------------------------------------|
         | 1. Kafka Consumer (Task 2)                   |
         | 2. Database Integration (Task 3)             |
         | 3. Incentive API Client (Task 4)             |
         | 4. Balance REST API (Task 5)                 |
         +----------------------+-----------------------+
                                |
                                v
                     +----------------------+
                     |     H2 Database      |
                     |    (UserRecords)     |
                     +----------------------+
```

---

## 🛠️ Tasks & Implementation Details

### 🟢 Task 1: Environment Setup & Project Configuration
- Configured the local development environment using **Java 17** and **Spring Boot**.
- Resolved Maven dependencies and verified the project structure using the Maven Wrapper (`./mvnw`).
- Updated the `application.yml` configuration for seamless Spring Boot integration.

### 🟢 Task 2: Kafka Consumer Implementation
- Implemented an Apache Kafka consumer to listen for incoming transaction events.
- Created `KafkaConsumer.java` under the `component` package.
- Verified message consumption using unit and integration tests (`TaskTwoTests.java`).

### 🟢 Task 3: Database & Transaction Processing Engine
- Connected the application to the persistence layer using `UserRepository` and `DatabaseConduit`.
- Processed incoming transactions between sender and recipient accounts.
- Implemented business validation logic:
  - Verified sender balance before processing.
  - Updated sender and recipient balances after successful transactions.

### 🟢 Task 4: External Incentive Service Integration
- Integrated the external **Transaction Incentive API** (`transaction-incentive-api.jar`).
- Created a REST client to send processed transactions.
- Applied incentive bonuses returned by the external API to recipient balances.

### 🟢 Task 5: Balance Query REST Controller
- Developed `BalanceController.java`.
- Implemented the `GET /balance` endpoint to retrieve account balances using `userId`.
- Returned structured `Balance` response objects while handling invalid users gracefully.

---

## 💻 Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Modules:** Spring Web, Spring Data JPA
- **Messaging:** Apache Kafka
- **Database:** H2 In-Memory Database
- **Build Tool:** Maven
- **Testing:** JUnit 5, Spring Boot Test
- **Version Control:** Git & GitHub

---

## ⚙️ Getting Started

### Prerequisites

- Java 17 SDK
- Git

### Clone the Repository

```bash
git clone https://github.com/fahadaslam18321-alt/forage-midas.git
cd forage-midas
```

### Build the Project

```bash
./mvnw clean install
```

### Run Tests

```bash
./mvnw test
```

### Run the Application

```bash
./mvnw spring-boot:run
```

---

## 📂 Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.jpmc.midascore
│   │       ├── component
│   │       ├── controller
│   │       ├── entity
│   │       ├── repository
│   │       └── service
│   └── resources
│       └── application.yml
└── test
    └── java
```

---

## ✨ Key Features

- ✅ Event-driven transaction processing with Apache Kafka
- ✅ Secure balance validation before fund transfers
- ✅ External incentive service integration
- ✅ REST API for real-time balance queries
- ✅ In-memory H2 database for rapid development and testing
- ✅ Unit and integration testing with JUnit 5

---

## 👨‍💻 Author

**Fahad Aslam**

- GitHub: https://github.com/fahadaslam18321-alt

---

## 📄 License

This project was completed as part of the **JPMorgan Chase & Co. Advanced Software Engineering Virtual Experience Program** on **Forage** and is intended for educational and portfolio purposes.
