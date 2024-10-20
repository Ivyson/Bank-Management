# Banking Management System 

This project is a **Banking Management System** developed in Java that simulates basic banking operations like **authentication**, **deposits**, **withdrawals**, and **inter-bank payments**. The system uses **JSON** files for data persistence, storing user details, balances, and transactional history.

### Key Features:
1. **User Authentication**: Secure login by verifying the account number and PIN.
2. **Deposit and Withdrawals**: Handles transactions with validation for deposit amounts and withdrawal limits.
3. **Inter-Bank Payments**: Allows users to make payments to other accounts and save beneficiaries for quick future transactions.
4. **Transaction History**: Maintains and displays the history of all user transactions.
5. **Balance Inquiry**: Displays the user's current balance.
6. **Beneficiaries**: Ability to add new beneficiaries or pay to previously added ones.
7. **Customizable Withdrawal Limit**: Users can update their own withdrawal limits.
8. **Account Management**: Features for account creation and deletion.
9. **Enhanced Security**: Password recovery using phone number and email verification.
10. **User Interface Enhancement**: Improved user interface with GUI or table-based text menus.
11. **Inter-Account Transfers**: Allows users to create nested accounts and transfer money within them.
12. **Bills Payment**: Enables users to pay registered public companies.
13. **Input Sanitization**: Ensures proper input validation and sanitation.

## Key Components:

1. **`User` Class**: 
   - Stores user information including account number, PIN, balance, transaction history, and a list of beneficiaries.
   - Methods to add transactions, update balance, and modify withdrawal limits.
   
2. **`Bank` Class**:
   - Central to managing users, transactions, deposits, withdrawals, and payments.
   - Responsible for loading and saving user data to/from a JSON file.
   - Implements payment functionality between users.
   
3. **`Main` Class**:
   - The entry point for running the system.
   - Contains a user-friendly menu for interacting with the banking system, such as choosing to deposit, withdraw, or make payments.
   - Manages inputs and enforces input validation.
   
4. **`Beneficiary` Class**:
   - Represents a payee's details such as their name and account number, saved for future payments.

5. **`Transactions` Class**:
   - Tracks transaction types (e.g., deposit, withdrawal, payment), amounts, and dates.
   
6. **JSON Data Storage**:
   - All user data (including transactions and beneficiaries) is stored in a `data.json` file, which is loaded when the system starts and saved after every transaction.

## Running the Project

### Prerequisites:
1. **Java**: Ensure you have JDK 8 or later installed.
2. **Maven**: This project uses Maven for dependency management.
3. **Jackson Databind**: To handle JSON serialization and deserialization.

### Steps to Run:
1. **Clone the Project**:
   ```bash
   git clone https://github.com/Ivyson/Banking-Management.git
   ```
2. **Navigate to the Project Directory**:
   ```bash
   cd banking-management-system
   ```
3. **Install Dependencies**:
   Maven will automatically fetch the necessary dependencies.
   ```bash
   mvn clean install
   ```
4. **Run the Project**:
   Execute the project using the `Main` class.
   ```bash
   mvn exec:java -Dexec.mainClass="banking.management.Main"
   ```
   
5. **Usage**:
   Once the program starts:
   - Enter your **account number** and **PIN** for authentication.
   - Select options from the menu to deposit, withdraw, check balance, view transaction history, or make a payment.

### Sample Workflow:
1. **Login**: Enter account number and PIN.
2. **Deposit**: Choose option `1`, enter an amount to deposit, and confirm the successful update of your balance.
3. **Withdraw**: Select option `2`, input the amount to withdraw, and see your new balance.
4. **Payments**: Select option `5`, choose a beneficiary, and make a payment.
5. **View Transactions**: Option `4` will display all previous transactions neatly.

## How It Works
Each feature is designed to be user-friendly with a clean, text-based interface. Whenever the user performs an operation, such as making a deposit or withdrawal, the system validates the input and updates the account data accordingly. Transaction history is updated in real-time, and all data is saved to JSON files, ensuring persistence across sessions.

### Notes:
- You can edit the `data.json` file to initialize your users and their balances.
- The system will not allow a user to **withdraw more than the default limit (R500)** unless the limit is updated.
- **Hidden password input** is used during PIN entry, replacing the typed digits with asterisks for security.

### Possible Advancements 
1. **Account Management**:
   - Account Creation   [Done]
   - Account Deletion [Done]
2. **Security Elevation**:
   - Password recovery using phone number and email verification.
3. **User Interface Enhancement**:
   - Use GUI for user friendliness, or present menus and outputs as tables for cleaner code.
4. **Inter-Account Transfers**:
   - Allow users to create nested accounts and transfer money within them.
5. **Bills Payment**:
   - Enable users to pay registered public companies (e.g., DSTV Multi-choice, CPUT Fees).
6. **Input Sanitization**:
   - Ensure proper input validation and sanitation.


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
└── [README.md](http://_vscodecontentref_/#%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22%2FUsers%2Fsam%2FDownloads%2Fbankingmangement%2FREADME.md%22%2C%22path%22%3A%22%2FUsers%2Fsam%2FDownloads%2Fbankingmangement%2FREADME.md%22%2C%22scheme%22%3A%22file%22%7D%7D)
 └── [data.json](http://_vscodecontentref_/#%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22%2FUsers%2Fsam%2FDownloads%2Fbankingmangement%2Fdata.json%22%2C%22path%22%3A%22%2FUsers%2Fsam%2FDownloads%2Fbankingmangement%2Fdata.json%22%2C%22scheme%22%3A%22file%22%7D%7D)