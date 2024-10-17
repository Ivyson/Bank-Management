package banking.management;

import java.io.Console;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        
        // Use Console for secure password input
        Console console = System.console();
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
        if (console != null) {
            char[] pinArray = console.readPassword("Enter PIN: ");
            inputPin = Integer.parseInt(new String(pinArray));
        } else {
            // Fallback to regular Scanner input if Console is not available
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

        User authenticatedUser = bank.authenticate(inputAccountNumber, inputPin);

        if (authenticatedUser != null) {
            System.out.printf("Welcome, %s %s!\n", authenticatedUser.getFirstName(), authenticatedUser.getLastName());

            while (true) {
                printMainMenu();

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
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = getPositiveDoubleInput(scanner);
                        bank.deposit(authenticatedUser, depositAmount);
                        break;
                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = getPositiveDoubleInput(scanner);
                        bank.withdraw(authenticatedUser, withdrawalAmount);
                        break;
                    case 3:
                        displayBalance(authenticatedUser);
                        break;
                    case 4:
                        displayTransactionHistory(authenticatedUser);
                        break;
                    case 5: // Payment options
                        processPayment(scanner, bank, authenticatedUser);
                        break;
                    case 6: // Update withdrawal limit
                        updateWithdrawalLimit(scanner, authenticatedUser);
                        break;
                    case 7: // View personal details
                        viewPersonalDetails(authenticatedUser);
                        break;
                    case 8:
                        System.out.println("Thank you for using our service. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid account number or PIN.");
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n================= Main Menu =================");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. View Transaction History");
        System.out.println("5. Inter-Bank Payment");
        System.out.println("6. Update Withdrawal Limit");
        System.out.println("7. View Personal Details");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }

    private static void displayBalance(User user) {
        System.out.printf("\nYour current balance is: R %.2f\n", user.getBalance());
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

    private static void updateWithdrawalLimit(Scanner scanner, User authenticatedUser) {
        System.out.print("Enter new withdrawal limit: ");
        double newLimit = getPositiveDoubleInput(scanner);
        authenticatedUser.setWithdrawalLimit(newLimit);
        System.out.printf("Withdrawal limit updated to R %.2f\n", newLimit);
    }

    private static void viewPersonalDetails(User user) {
        System.out.println("\n================= Personal Details ==================");
        System.out.printf("Account Number: %d\n", user.getAccountNumber());
        System.out.printf("Name: %s %s\n", user.getFirstName(), user.getLastName());
        System.out.printf("Current Balance: R %.2f\n", user.getBalance());
        System.out.printf("Withdrawal Limit: R %.2f\n", user.getWithdrawalLimit());
        System.out.println("=======================================================");
    }

    private static double getPositiveDoubleInput(Scanner scanner) {
        double value = 0;
        while (true) {
            try {
                value = scanner.nextDouble();
                if (value > 0) {
                    break; // Exit loop if input is valid
                } else {
                    System.out.println("Please enter a positive amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
                scanner.next(); // Clear the invalid input
            }
        }
        return value;
    }
}
