import java.util.Scanner;

public class MenuFrame {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number: ");
        long inputAccountNumber = scanner.nextLong();
        System.out.print("Enter PIN: ");
        int inputPin = scanner.nextInt();

        User authenticatedUser = bank.authenticate(inputAccountNumber, inputPin);
        if (authenticatedUser != null) {
            System.out.println("Login successful!");
            System.out.println("Hello " + authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName() + ", How can we help you today?");
            while(true){
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Check You Balance");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
    
                switch (option) {
                    case 1:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        bank.deposit(authenticatedUser, depositAmount);
                        break;
                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        bank.withdraw(authenticatedUser, withdrawalAmount);
                        break;
                    case 3:
                        System.out.println("You have R"+bank.getBalance(authenticatedUser));
                        break;
                    case 4:
                        System.out.println("Thank You for using our services... Good Bye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
           
            }
        } else {
            System.out.println("Invalid account number or PIN.");
        }

        scanner.close();
    }
}