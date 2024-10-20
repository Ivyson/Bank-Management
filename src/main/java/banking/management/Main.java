package banking.management;
import  java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@(gmail\\.com|cput\\.ac\\.za|outlook\\.com|yahoo\\.com|hotmail\\.com)$";
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        clearConsole();
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int option = 0;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
                continue; // Restart the loop
            }
            clearConsole();
            switch (option) {
                case 1:
                    login(bank, scanner);
                    break;
                case 2:
                    createAccount(bank, scanner);
                    break;
                case 3:
                    System.out.println("Thank you for using our service. Goodbye!");
                    System.exit(0);
                default: 
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void login(Bank bank, Scanner scanner) {
        long inputAccountNumber = 0;
        int inputPin = 0;
    
        // Handle account number input
        while (true) {
            try {
                System.out.print("Enter account number: ");
                inputAccountNumber = scanner.nextLong();
                break; // Exit loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid account number. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
    
        // Handle PIN input
        while (true) {
            try {
                Console console = System.console();
                if (console != null) {
                    char[] pinArray = console.readPassword("Enter PIN: ");
                    inputPin = Integer.parseInt(new String(pinArray));
                } else {
                
                    while (true) {
                        try {
                            System.out.print("Enter PIN: ");
                            inputPin = scanner.nextInt();
                            break; // Exit loop if input is valid
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid PIN. Please enter a valid 4-digit PIN.");
                            scanner.next(); // Clear the invalid input
                        }
                    }
                }
                break; // Exit loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid PIN. Please enter a valid 4-digit PIN.");
                scanner.next(); // Clear the invalid input
            }
        }
    
        User authenticatedUser = bank.authenticate(inputAccountNumber, inputPin);
    
        if (authenticatedUser != null) {
            clearConsole();
            System.out.printf("Welcome, %s %s!\n", authenticatedUser.getFirstName(), authenticatedUser.getLastName());
            userMenu(bank, authenticatedUser, scanner);
        } else {
            System.out.println("Invalid account number or PIN.");
        }
    }
    private static void createAccount(Bank bank, Scanner scanner) {
        System.out.print("Enter first name: ");
        String firstName = scanner.next();

        System.out.print("Enter last name: ");
        String lastName = scanner.next();

        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.next();
            if (email.matches(EMAIL_REGEX)) {
                break;
            } else {
                System.out.println("Invalid email. Please enter an email ending with @gmail.com, @cput.ac.za, @outlook.com, @yahoo.com, or @hotmail.com.");
            }
        }

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.next();

        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = 0;
        try {
            initialDeposit = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount. Please enter a number.");
            scanner.next(); // Clear invalid input
            return;
        }

        long accountNumber = System.currentTimeMillis(); // generate a unique account number
        int pin = (int) (Math.random() * 9000) + 1000; // Generate a random 4-digit PIN

        User newUser = new User();
        newUser.setAccountNumber(accountNumber);
        newUser.setPin(pin);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setBalance(initialDeposit);
        newUser.setWithdrawalLimit(500.0); // Default withdrawal limit

        bank.addUser(newUser);
        bank.saveUsersToJSON();

        System.out.printf("Account created successfully! Your account number is %d and your PIN is %d.\n", accountNumber, pin);
    }

    public static void clearConsole() {
        // Check if the console supports ANSI escape codes
        if (System.console() != null) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    private static void userMenu(Bank bank, User authenticatedUser, Scanner scanner) {
        while (true) {
            System.out.println("\n================= Main Menu =================");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. View Transaction History");
            System.out.println("5. Inter-Bank Payment");
            System.out.println("6. Update Withdrawal Limit");
            System.out.println("7. View My Details");
            System.out.println("8. Change Email");
            System.out.println("9. Change Phone Number");
            System.out.println("10. Change PIN");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");

            int option = 0;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
                continue; // Restart the loop
            }

            switch (option) {
                case 1:
                    clearConsole();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = 0;
                    try {
                        depositAmount = scanner.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid deposit amount. Please enter a number.");
                        scanner.next(); // Clear invalid input
                        continue;
                    }
                    
                    bank.deposit(authenticatedUser, depositAmount);
                    break;
                case 2:
                clearConsole();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = 0;
                    try {
                        withdrawalAmount = scanner.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid withdrawal amount. Please enter a number.");
                        scanner.next(); 
                        continue;
                    }
                    bank.withdraw(authenticatedUser, withdrawalAmount);
                    break;
                case 3:
                    clearConsole();
                    System.out.printf("Your current balance is: R %.2f\n", authenticatedUser.getBalance());
                    break;
                case 4:
                    clearConsole();
                    displayTransactionHistory(authenticatedUser);
                    // System.out.println("Transaction History:");
                    // authenticatedUser.getTransactionHistory().forEach(System.out::println);
                    break;
                case 5:
                    clearConsole();
                    processPayment(scanner, bank, authenticatedUser);
                    break;
                case 6:
                clearConsole();
                    System.out.print("Enter new withdrawal limit: ");
                    double newLimit = 0;
                    try {
                        newLimit = scanner.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid limit amount. Please enter a number.");
                        scanner.next(); // Clear invalid input
                        continue;
                    }
                    bank.updateWithdrawalLimit(authenticatedUser, newLimit);
                    // clearConsole();
                    break;
                case 7:
                    clearConsole();
                    bank.viewPersonalDetails(authenticatedUser);
                    break;
                case 8:
                clearConsole();
                String newEmail;
                while (true) {
                    System.out.print("Enter new email: ");
                    newEmail = scanner.next();
                    if (newEmail.matches(EMAIL_REGEX)) {
                        break;
                    } else {
                        System.out.println("Invalid email. Please enter an email ending with @gmail.com, @cput.ac.za, @outlook.com, @yahoo.com, or @hotmail.com.");
                    }
                }
                bank.changeEmail(authenticatedUser, newEmail);
                // clearConsole();
                    break;
                case 9:
                clearConsole();
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.next();
                    bank.changePhoneNumber(authenticatedUser, newPhoneNumber);
                    break;
                case 10:
                    clearConsole();
                    int newPin = 0;
                    int cPin = 0;
                    int count = 0;
                    while (true) {
                        try {
                            System.out.print("Enter new PIN: ");
                            newPin = scanner.nextInt();
                            System.out.print("Confirm your PIN: ");
                            cPin = scanner.nextInt();
                            if (newPin == cPin) {
                                bank.changePin(authenticatedUser, newPin);
                                // System.out.println("PIN updated successfully.");
                                break;
                            } else {
                                System.out.println("PINs do not match. Please try again.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid PIN. Please enter a valid 4-digit PIN.");
                            scanner.next();
                        }
                        count++;
                        if(count == 3){
                            break;
                        }
                    }
                    break;
                case 11:
                    System.out.println("Thank you for using our service. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void payNewBeneficiary(Scanner scanner, Bank bank, User authenticatedUser) {
        System.out.print("Enter recipient's name: ");
        String name = scanner.nextLine();

        System.out.print("Enter recipient's account number: ");
        long recipientAccountNumber = scanner.nextLong();

        User payee = bank.findUserByNameOrAccount(name, recipientAccountNumber);
        if (payee != null) {
            if (payee.getAccountNumber() == authenticatedUser.getAccountNumber()) {
                System.out.println("You cannot Pay Yourself!");
                return;
            }

            System.out.printf("Recipient found: %s %s (Account Number: %d)\n", 
                payee.getFirstName(), payee.getLastName(), payee.getAccountNumber());

            System.out.print("Enter payment amount: ");
            double paymentAmount = getPositiveDoubleInput(scanner);

            if (authenticatedUser.getBalance() < paymentAmount) {
                System.out.println("Insufficient balance for this transaction.");
            } else {
                bank.pay(authenticatedUser, payee, paymentAmount);
                System.out.printf("Payment of R %.2f successful!\n", paymentAmount);
            }
        } else {
            System.out.println("Payee not found.");
        }
    }

    private static double getPositiveDoubleInput(Scanner scanner) {
        double value = 0;
        while (true) {
            try {
                value = scanner.nextDouble();
                if (value > 0) {
                    break; // Exits loop if input is valid
                } else {
                    System.out.println("Please enter a positive amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
                scanner.next(); // Clears the invalid input
            }
        }
        return value;
    }

    private static void payRecentBeneficiary(Scanner scanner, Bank bank, User authenticatedUser) {
        if (authenticatedUser.getBeneficiaries().isEmpty()) {
            System.out.println("No beneficiaries found.");
            return;
        }

        System.out.println("Select a beneficiary:");
        for (int i = 0; i < authenticatedUser.getBeneficiaries().size(); i++) {
            System.out.printf("%d. %s\n", i + 1, authenticatedUser.getBeneficiaries().get(i));
        }

        System.out.print("Choose a beneficiary: ");
        int beneficiaryIndex = scanner.nextInt() - 1;

        if (beneficiaryIndex < 0 || beneficiaryIndex >= authenticatedUser.getBeneficiaries().size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Beneficiary selectedBeneficiary = authenticatedUser.getBeneficiaries().get(beneficiaryIndex);
        System.out.print("Enter payment amount: ");
        double paymentAmount = getPositiveDoubleInput(scanner);
        
        User recipientUser = bank.findUserByAccountNumber(selectedBeneficiary.getAccountNumber());
        if (recipientUser != null) {
            bank.pay(authenticatedUser, recipientUser, paymentAmount);
        } else {
            System.out.println("Recipient not found.");
        }
    }

    private static void processPayment(Scanner scanner, Bank bank, User authenticatedUser) {
        System.out.println("1. Pay a new beneficiary");
        System.out.println("2. Pay a recently paid beneficiary");
        System.out.print("Choose an option: ");
        int payOption = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        if (payOption == 1) {
            payNewBeneficiary(scanner, bank, authenticatedUser);
        } else if (payOption == 2) {
            payRecentBeneficiary(scanner, bank, authenticatedUser);
        } else {
            System.out.println("Invalid option. Please try again.");
        }
    }
    private static void displayTransactionHistory(User user) {
        System.out.println("\n================ Transaction History ================");
        System.out.printf("%-15s %-10s %s\n", "Date", "Type", "Amount");
        System.out.println("-------------------------------------------------------");
        user.getTransactionHistory().forEach(transaction -> 
            System.out.printf("%-15s %-10s R %.2f\n", 
                transaction.getDate(), transaction.getType(), transaction.getAmount()));
        System.out.println("=======================================================");
    }
}