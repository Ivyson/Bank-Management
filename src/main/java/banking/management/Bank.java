package banking.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Bank {
    private List<User> users;
    private static final double TAX_RATE = 0.02; // 2% tax rate
    // private static final double DEFAULT_WITHDRAWAL_LIMIT = 500.0;

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
            double taxedAmount = amount - (amount * TAX_RATE);
            user.setBalance(user.getBalance() + taxedAmount);
            user.addTransaction("deposit", amount);
            user.addTransaction("Admin Fee", -(amount * TAX_RATE)); 
            System.out.printf("Deposit successful! Your new balance is: R %.2f\n", user.getBalance());
            saveUsersToJSON();
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(User user, double amount) {
        if (amount > 0 && user.getBalance() >= amount && amount <= user.getWithdrawalLimit()) {
            double tax = amount * TAX_RATE;
            double taxedAmount = amount + tax;
            if (user.getBalance() >= taxedAmount) {
                user.setBalance(user.getBalance() - taxedAmount);
                user.addTransaction("withdrawal", amount);
                user.addTransaction("Admin Fee", -tax); 
                System.out.printf("Withdrawal successful! Your new balance is: R %.2f\nTaxed Amount is: R %.2f\n", user.getBalance(), tax);
                saveUsersToJSON();
            } else {
                System.out.println("Insufficient funds after tax.");
            }
        } else {
            System.out.println("Insufficient funds, invalid withdrawal amount, or exceeds withdrawal limit.");
        }
    }

    public void updateWithdrawalLimit(User user, double newLimit) {
        user.setWithdrawalLimit(newLimit);
        System.out.printf("Withdrawal limit updated to: R %.2f\n", newLimit);
        saveUsersToJSON();
    }

    public void viewPersonalDetails(User user) {
        System.out.println("\n================= Personal Details ==================");
        System.out.printf("Account Number: %d\n", user.getAccountNumber());
        System.out.printf("Name: %s %s\n", user.getFirstName(), user.getLastName());
        System.out.printf("Email Address: %s\n",user.getEmail());
        System.out.printf("Phone Number: %s\n",user.getPhoneNumber());
        System.out.printf("Current Balance: R %.2f\n", user.getBalance());
        System.out.printf("Withdrawal Limit: R %.2f\n", user.getWithdrawalLimit());
        // System.out.printf("")
        System.out.println("=======================================================");
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void changeEmail(User user, String newEmail) {
        user.setEmail(newEmail);
        saveUsersToJSON();
        System.out.println("Email updated successfully.");
    }

    public void changePhoneNumber(User user, String newPhoneNumber) {
        user.setPhoneNumber(newPhoneNumber);
        saveUsersToJSON();
        System.out.println("Phone number updated successfully.");
    }

    public void changePin(User user, int newPin) {
        user.setPin(newPin);
        saveUsersToJSON();
        System.out.println("PIN updated successfully.");
    }

    private List<User> loadUsersFromJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("data.json");

        if (!file.exists()) {
            throw new IOException("data.json file not found.");
        }
        UsersWrapper usersWrapper = objectMapper.readValue(file, UsersWrapper.class);
        return usersWrapper.getUsers();
    }

    public void saveUsersToJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        try {
            File file = new File("data.json");
            UsersWrapper usersWrapper = new UsersWrapper();
            usersWrapper.setUsers(users);
            writer.writeValue(file, usersWrapper);
            // System.out.println("User data saved successfully to " + file.getAbsolutePath()); //Useful For debuggin, should comment it out when whensubmitting
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

}