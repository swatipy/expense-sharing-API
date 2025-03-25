package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ExpenseSharingInput {
    private final String expenseSharingCommand;
    private final String paidBy;
    private final double amountPaid;
    private final int numberOfUsers;
    private final List<String> userIds;
    private final String splitType;
    private final List<Double> values;

    public ExpenseSharingInput(String command, String paidBy, double amountPaid, int numberOfUsers, List<String> userIds, String splitType, List<Double> values) {
        this.expenseSharingCommand = command;
        this.paidBy = paidBy;
        this.amountPaid = amountPaid;
        this.numberOfUsers=numberOfUsers;
        this.userIds = userIds;
        this.splitType = splitType;
        this.values = values;
    }

    public static ExpenseSharingInput parse(String command) {
        String[] parts = command.split(" ");
        List<String> userIds=new ArrayList<>();
        String splitType;
        List<Double> values=new ArrayList<>();


        if (parts[0].equals("EXPENSE")) {
            String paidBy= parts[1];
            double amountPaid = Double.parseDouble(parts[2]);
            int numOfUsers = Integer.parseInt(parts[3]);

            for (int i = 0; i < numOfUsers; i++) {
                userIds.add(parts[4 + i]);
            }
            splitType = parts[4 + numOfUsers];

            for (int i = 5 + numOfUsers; i < parts.length; i++) {
                values.add(Double.parseDouble(parts[i]));
            }
            return new ExpenseSharingInput("EXPENSE", paidBy, amountPaid, numOfUsers,userIds, splitType, values);
        } else if (parts[0].equals("SHOW")) {
            return new ExpenseSharingInput("SHOW", parts.length > 1 ? parts[1] : null, 0, 0, null, null, null);
        }
        throw new IllegalArgumentException("Invalid command format");
    }

    // Getters
    public String getExpenseSharingCommand() { return expenseSharingCommand; }
    public String getPaidBy() { return paidBy; }
    public double getAmountPaid() { return amountPaid; }
    public int getNumberOfUsers() {return numberOfUsers;}
    public List<String> getUserIds() { return userIds; }
    public String getSplitType() { return splitType; }
    public List<Double> getValues() { return values; }
}

