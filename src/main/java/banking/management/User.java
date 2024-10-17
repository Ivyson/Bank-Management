package banking.management;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private long accountNumber;
    private int pin;
    private String firstName;
    private String lastName;
    private double balance;
    private double withdrawalLimit;
    private List<Transactions> transactionHistory;
    private List<Beneficiary> beneficiaries;

    public User() {
        this.withdrawalLimit = 500.0; // Default withdrawal limit
        this.transactionHistory = new ArrayList<>();
        this.beneficiaries = new ArrayList<>();
    }

    public long getAccountNumber() {
        return accountNumber;   
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    public List<Transactions> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(String type, double amount) {
        this.transactionHistory.add(new Transactions(type, amount));
    }

    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public void addBeneficiary(String name, long accountNumber) {
        this.beneficiaries.add(new Beneficiary(name, accountNumber));
    }
    
  
        
}