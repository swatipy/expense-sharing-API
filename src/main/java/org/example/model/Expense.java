package org.example.model;
import java.util.List;

public class Expense {
    private String paidBy;
    private double amountPaid;
    private List<String> userIds;
    private String splitType;
    private List<Double> values;

    public Expense(String paidBy, double amountPaid, int numberOfUsers, List<String> userIds, String splitType, List<Double> values) {
        this.paidBy = paidBy;
        this.amountPaid = amountPaid;
        this.userIds = userIds;
        this.splitType = splitType;
        this.values = values;
    }

    // Getters

    public Expense()
    {

    }
    public String getPaidBy() { return paidBy; }
    public double getAmountPaid() { return amountPaid; }
    public List<String> getUserIds() { return userIds; }
    public String getSplitType() { return splitType; }
    public List<Double> getValues() { return values; }
}

