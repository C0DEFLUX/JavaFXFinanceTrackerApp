package model;

public class Transactions
{
    private String type;
    private String description;
    private double amount;

    public Transactions(String type, String description, double amount)
    {
        this.type = type;
        this.description = description;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
