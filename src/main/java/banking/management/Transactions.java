package banking.management;
public class Transactions {
    private String type;
    private double amount;
    private String date;

    public Transactions() {
        // Default constructor for Jackson
    }

    public Transactions(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = java.time.LocalDate.now().toString();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%s: R %.2f on %s", type, amount, date);
    }
}
