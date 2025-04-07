package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpenseSharingInput implements UserBalanceInterface {
    private  ExpenseSharingCommand command;

    private Expense expense; // If the command is add expense then expense will be present
    private String userId; // If the command is show balance then userId will be present

    public ExpenseSharingInput() {
        // No-arg constructor needed for deserialization
    }

    public ExpenseSharingInput(ExpenseSharingCommand command, Expense expense) {
        this.command = command;
        this.expense = expense;
    }

    public ExpenseSharingInput(ExpenseSharingCommand command, String userId) {
        this.command = command;
        this.userId = userId;
    }

    public ExpenseSharingInput(ExpenseSharingCommand expenseSharingCommand) {
        this.command=expenseSharingCommand;
    }

    public ExpenseSharingCommand getCommand() {
        return command;
    }

    public Expense getExpense() {
        return expense;
    }

    public Optional<String> getUserId() {
        return Optional.ofNullable(userId);
    }
}
