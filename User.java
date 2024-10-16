import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private long accountNumber;
    private int pin;
    private int userId;
    private double balance;

    public User(String firstName, String lastName, long accountNumber, int pin, int userId, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.userId = userId;
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }
    public int Id(){
        return userId;
    }
    public int getPin() {
        return pin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static List<User> predefinedUsers = new ArrayList<>();

    static {
        predefinedUsers.add(new User("Sam", "Boy", 123456789, 1234, 1, 1000.0));
        predefinedUsers.add(new User("Gift", "Sam", 987654321, 4321, 2, 2000.0));
        predefinedUsers.add(new User("Alice", "Johnson", 111222333, 1111, 3, 1500.0));
        predefinedUsers.add(new User("Bob", "Brown", 444555666, 2222, 4, 2500.0));
        predefinedUsers.add(new User("Bill", "Davis", 777888999, 3333, 5, 3000.0));
    }
}
