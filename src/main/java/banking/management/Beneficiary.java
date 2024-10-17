package banking.management;

public class Beneficiary {
    private String name;
    private long accountNumber;
    public Beneficiary(){
        //Thhs is for Jackson
    }
    public Beneficiary(String name, long accountNumber) {
        this.name = name;
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return String.format("Beneficiary Name: %s\t Account Number: %d", name, accountNumber);
    }
}
