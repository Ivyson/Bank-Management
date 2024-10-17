package banking.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
// import java.io.InputStream;
import java.util.List;

public class Bank {
    private List<User> users;

    public Bank() {
        try {
            users = loadUsersFromJSON();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load user data from JSON.");
        }
    }

    public User authenticate(long accountNumber, int pin) {
        return users.stream()
                .filter(user -> user.getAccountNumber() == accountNumber && user.getPin() == pin)
                .findFirst()
                .orElse(null);
    }

    public void deposit(User user, double amount) {
        if (amount > 0) {
            user.setBalance(user.getBalance() + amount);
            user.addTransaction("deposit", amount);
            System.out.printf("Deposit successful! Your new balance is: R %.2f\n", user.getBalance());
            saveUsersToJSON();
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(User user, double amount) {
        double taxRate = 0.01; // Assuming 1% tax
        double tax = amount * taxRate;
        double totalAmount = amount + tax;
    
        if (amount > user.getWithdrawalLimit()) {
            System.out.println("Withdrawal amount exceeds your limit of R " + user.getWithdrawalLimit());
            return; // Exit the method
        }
    
        if (amount > 0 && user.getBalance() >= totalAmount) {
            user.setBalance(user.getBalance() - totalAmount);
            user.addTransaction("withdrawal", amount);
            System.out.printf("Withdrawal successful! Amount: R %.2f, Tax: R %.2f, New balance: R %.2f\n", 
                              amount, tax, user.getBalance());
            saveUsersToJSON();
        } else {
            System.out.println("Insufficient funds or invalid withdrawal amount.");
        }
    }
    
    

    private List<User> loadUsersFromJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("data.json"); // Use the same path as above
    
        if (!file.exists()) {
            throw new IOException("data.json file not found.");
        }
        UsersWrapper usersWrapper = objectMapper.readValue(file, UsersWrapper.class);
        return usersWrapper.getUsers();
    }
    public void pay(User payer, User payee, double amount) {
        if (amount > 0 && payer.getBalance() >= amount) {
            // Deduct amount from payer's account
            payer.setBalance(payer.getBalance() - amount);
            payer.addTransaction("pay-out", amount); // Log pay-out transaction
    
            // Add amount to payee's account
            payee.setBalance(payee.getBalance() + amount);
            payee.addTransaction("pay-in", amount); // Log pay-in transaction
    
            // Save updated user data to JSON
            saveUsersToJSON();
            
            System.out.printf("Payment of R %.2f successful from %s to %s!\n", amount, payer.getFirstName(), payee.getFirstName());
        } else {
            System.out.println("Insufficient funds or invalid payment amount.");
        }
    }
    public User getUserByAccountNumber(long accountNumber) {
        return users.stream()
                .filter(user -> user.getAccountNumber() == accountNumber)
                .findFirst()
                .orElse(null);
    }
            
    
    public void updateWithdrawalLimit(User user, double newLimit) {
        if (newLimit > 0) {
            user.setWithdrawalLimit(newLimit);
            System.out.printf("Withdrawal limit updated successfully! New limit: R %.2f\n", newLimit);
            saveUsersToJSON();
        } else {
            System.out.println("Invalid withdrawal limit. Please enter a positive amount.");
        }
    }

    public void saveUsersToJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        try {
            // Save to an external file
            File file = new File("data.json"); 
            UsersWrapper usersWrapper = new UsersWrapper();
            usersWrapper.setUsers(users);
            writer.writeValue(file, usersWrapper);
            System.out.println("User data saved successfully to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save user data to JSON.");
        }
    }
    public User findUserByAccountNumber(long accountNumber) {
        return users.stream()
                .filter(user -> user.getAccountNumber() == accountNumber)
                .findFirst()
                .orElse(null); // Return null if the user is not found
    }
    public User findUserByNameOrAccount(String name, long accountNumber) {
        // Try to find by name first
        for (User user : users) {
            if (user.getFirstName().equalsIgnoreCase(name) || user.getLastName().equalsIgnoreCase(name)) {
                return user; // Return user found by name
            }
        }
        // If not found by name, check by account number
        return users.stream()
                .filter(user -> user.getAccountNumber() == accountNumber)
                .findFirst()
                .orElse(null); // Return null if the user is not found
    }
    public void viewUserDetails(User user) {
        System.out.printf("Account Holder: %s %s\n", user.getFirstName(), user.getLastName());
        System.out.printf("Account Number: %d\n", user.getAccountNumber());
        System.out.printf("Balance: R %.2f\n", user.getBalance());
        System.out.printf("Withdrawal Limit: R %.2f\n", user.getWithdrawalLimit());
        System.out.println("Transaction History:");
        user.getTransactionHistory().forEach(System.out::println);
        System.out.println("Beneficiaries:");
        user.getBeneficiaries().forEach(System.out::println);
    }
    
}