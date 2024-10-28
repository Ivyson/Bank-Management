# Banking Management System

This **Banking Management System** is a Java-based project simulating essential banking operations like **authentication**, **deposits**, **withdrawals**, and **inter-bank payments**. It uses **JSON** for data storage to persist user information, account balances, and transaction history.

## Key Features
1. **User Authentication**: Secure login by verifying account numbers and PINs.
2. **Deposit and Withdrawals**: Supports deposits and withdrawals with validation for deposit amounts and withdrawal limits.
3. **Inter-Bank Payments**: Facilitates payments to other accounts and allows saving beneficiaries for quicker transactions.
4. **Transaction History**: Tracks and displays all transactions for each user.
5. **Balance Inquiry**: Allows users to view their current balance.
6. **Beneficiaries**: Enables adding and managing beneficiaries for simplified payments.
7. **Customizable Withdrawal Limits**: Users can set their own withdrawal limits.
8. **Account Management**: Provides account creation and deletion features.
9. **Input Validation**: Ensures sanitized inputs to maintain data integrity.

## Core Components

1. **`User` Class**  
   - Stores account information including account number, PIN, balance, transaction history, and a list of beneficiaries.
   - Provides methods to add transactions, update balance, and set withdrawal limits.
   
2. **`Bank` Class**  
   - Manages users, transactions, deposits, withdrawals, and payments.
   - Responsible for loading and saving user data to/from a JSON file.
   - Implements inter-user payment functionality.
   
3. **`Main` Class**  
   - Entry point for running the system.
   - Presents a user-friendly menu for actions like deposits, withdrawals, and payments.
   - Handles input validation.
   
4. **`Beneficiary` Class**  
   - Represents a payee’s details (name and account number) and saves them for future payments.

5. **`Transactions` Class**  
   - Tracks transaction types (deposit, withdrawal, payment), amounts, and dates.
   
6. **JSON Data Storage**  
   - Stores all user data, including transactions and beneficiaries, in a `data.json` file that loads on startup and saves after every transaction.

## Running the Project

### Prerequisites
1. **Java**: JDK 8 or later.
2. **Maven**: For dependency management.
3. **Jackson Databind**: Handles JSON serialization/deserialization.

### Setup and Execution
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Ivyson/Bank-Management.git
   ```
2. **Navigate to Project Directory**:
   ```bash
   cd bank-management
   ```
3. **Install Dependencies**:
   Maven will fetch required dependencies.
   ```bash
   mvn clean install
   ```
4. **Run the Application**:
   Launch the project from the `Main` class.
   ```bash
   mvn exec:java -Dexec.mainClass="banking.management.Main"
   ```
5. **Usage Instructions**:
   - Enter your **account number** and **PIN** to log in.
   - Choose menu options to deposit, withdraw, check balance, view transaction history, or make payments.
   - **Note**: Pre-existing user account details are available in the `data.json` file.

### Sample Workflow
1. **Login**: Input account number and PIN.
2. **Deposit**: Select option `1`, enter the deposit amount, and confirm the new balance.
3. **Withdraw**: Choose option `2`, enter the withdrawal amount, and see the updated balance.
4. **Payments**: Select option `5`, choose a beneficiary, and make a payment.
5. **View Transactions**: Select option `4` to display transaction history.

## System Overview
This project uses a user-friendly text-based interface. Each operation, such as deposits or withdrawals, validates input, updates account information, and saves data to JSON for persistence. Transaction history updates in real-time, ensuring data accuracy across sessions.

### Development Roadmap
1. **Account Management**:  
   - [x] Account Creation  
   - [x] Account Deletion  
2. **Security Improvements**:
   - Password recovery through phone number and email verification (To-do).
3. **UI Enhancement**:
   - Future implementation of a GUI or table-based menus (To-do).
4. **Inter-Account Transfers**:
   - Nested account support for transfers (To-do).
5. **Bill Payment**:
   - Enable bill payments to public entities like DSTV or educational fees (To-do).
6. **Input Sanitization**:
   - Completed validation to prevent errors and maintain security.

## Project Structure

```plaintext
src
├── main
│   ├── java
│   │   └── banking
│   │       └── management
│   │           ├── Bank.java
│   │           ├── Main.java
│   │           ├── User.java
│   │           ├── Transactions.java
│   │           ├── Beneficiary.java
│   │           └── UsersWrapper.java
│   └── resources
├── test
│   └── java
│       └── banking
│           └── management
│               └── BankTest.java
├── README.md
└── data.json
```
