import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<User> users;
    // User user = n
    public Bank() {
        users = new ArrayList<>(User.predefinedUsers);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User authenticate(long accountNumber, int pin) {
        for (User user : users) {
            if (user.getAccountNumber() == accountNumber && user.getPin() == pin) {
                return user;
            }
        }
        return null;
    }

    // Deposit
    public void deposit(User user, double amount) {
        if (amount > 0) {
            user.setBalance(user.getBalance() + amount);
            System.out.println("Deposit was a success. R" + user.getBalance() + " is your new balance.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Withdrawal
    public void withdraw(User user, double amount) {
        if (amount > 0 && user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            System.out.println("Withdrawal was a success. R" + user.getBalance() + " is your new balance.");
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }
    public double getBalance(User user){
        return user.getBalance();
    }
    public long getAccountNumber(User user) {
        return user.getAccountNumber();
    }

    public int getPin(User user) {
        return user.getPin();
    }

    public String getFirstName(User user) {
        return user.getFirstName();
    }

    public String getLastName(User user) {
        return user.getLastName();
    }
}
