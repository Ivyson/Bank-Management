import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    private Bank bank;

    public Frame() {
        bank = new Bank();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Bank Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel accountNumberLabel = new JLabel("Account Number:");
        JTextField accountNumberField = new JTextField();
        JLabel pinLabel = new JLabel("PIN:");
        JPasswordField pinField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(accountNumberLabel);
        panel.add(accountNumberField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(new JLabel()); // Empty cell
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long accountNumber = Long.parseLong(accountNumberField.getText());
                int pin = Integer.parseInt(new String(pinField.getPassword()));
                User authenticatedUser = bank.authenticate(accountNumber, pin);
                if (authenticatedUser != null) {
                    showOptions(authenticatedUser);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid account number or PIN.", "Login Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void showOptions(User user) {
        JFrame optionsFrame = new JFrame("Welcome");
        optionsFrame.setSize(400, 300);
        optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        optionsFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel greetingLabel = new JLabel("Hello " + user.getFirstName() + " " + user.getLastName() + ", How can we help you today?");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton exitButton = new JButton("Exit");

        panel.add(greetingLabel);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(checkBalanceButton);
        panel.add(exitButton);

        optionsFrame.add(panel);
        optionsFrame.setVisible(true);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog("Enter deposit amount:");
                double amount = Double.parseDouble(amountStr);
                if (amount > 0) {
                    bank.deposit(user, amount);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid deposit amount.", "Deposit Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog("Enter withdrawal amount:");
                double amount = Double.parseDouble(amountStr);
                if (amount > 0 && user.getBalance() >= amount) {
                    bank.withdraw(user, amount);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid withdrawal amount or insufficient funds.", "Withdrawal Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You have R" + bank.getBalance(user));
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsFrame.dispose();
                JOptionPane.showMessageDialog(null, "Thank You for using our services... Good Bye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }
}