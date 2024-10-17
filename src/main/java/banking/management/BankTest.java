// package banking.management;
// import java.util.*;

// public class BankTest {
//     private Bank bank;
//     private User user;

    
//     public void setUp() {
//         bank = new Bank();
//         user = new User();
//         user.setAccountNumber(123456789);
//         user.setPin(1234);
//         user.setFirstName("John");
//         user.setLastName("Doe");
//         user.setBalance(1000.0);
//         user.setWithdrawalLimit(500.0);

//         List<User> users = new ArrayList<>();
//         users.add(user);
//         bank.saveUsersToJSON();
//     }

 
//     public void testDepositTax() {
//         double initialBalance = user.getBalance();
//         bank.deposit(user, 100.0);
//         // assertEquals(initialBalance + 98.0, user.getBalance(), 0.01); // 2% tax
//     }


//     public void testWithdrawTax() {
//         double initialBalance = user.getBalance();
//         bank.withdraw(user, 100.0);
//         // assertEquals(initialBalance - 102.0, user.getBalance(), 0.01); // 2% tax
//     }

//     // @Test
//     public void testWithdrawLimit() {
//         bank.withdraw(user, 600.0);
//         // assertEquals(1000.0, user.getBalance(), 0.01); // Should not withdraw
//     }

//     // @Test
//     // public void testUpdateWithdrawalLimit() {
//     //     bank.updateWithdrawalLimit(user, 1000.0);
//         // assertEquals(1000.0, user.getWithdrawalLimit(), 0.01);
//     }

//     // @Test
//     // public void testViewUserDetails() {
//     //     bank.viewUserDetails(user);
//     //     // Check console output manually or use a library to capture console output
//     // }
// }