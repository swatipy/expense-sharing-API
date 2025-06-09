package org.example.controller;

import org.example.model.*;
import org.example.service.ExpenseSharingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api")
public class ExpenseSharingController {

    @Autowired
    private ExpenseSharingService expenseSharingService;

    //ExpenseSharingService expenseSharingApp = new ExpenseSharingService();

    String output="";


//Add/Update Balances
    @PostMapping("/expenses")
     public void updateBalance(@RequestBody ExpenseSharingInput expenseSharingInput) {

        List<UserBalance> userBalances;
        ExpenseSharingBalanceOutput expenseSharingBalanceOutput;
        switch (expenseSharingInput.getCommand()) {
            case EXPENSE:
                userBalances = expenseSharingService.addExpense(expenseSharingInput.getExpense());
                expenseSharingBalanceOutput = new ExpenseSharingBalanceOutput(userBalances);
                output = ExpenseSharingController.createOutput(expenseSharingBalanceOutput);
                break;
            case SHOW_BALANCE, SHOW_BALANCE_FOR_A_USER:
                userBalances = expenseSharingService.showBalances();
                expenseSharingBalanceOutput = new ExpenseSharingBalanceOutput(userBalances);
                output = ExpenseSharingController.createOutput(expenseSharingBalanceOutput);
                break;
        }
        System.out.println(output);

    }
//Show balances
    @GetMapping("/show")
    public void updateBalances(@RequestBody ExpenseSharingInput expenseSharingInput) {

        List<UserBalance> userBalances;
        ExpenseSharingBalanceOutput expenseSharingBalanceOutput;
        switch (expenseSharingInput.getCommand()) {
            case EXPENSE:
                userBalances = expenseSharingService.addExpense(expenseSharingInput.getExpense());
                expenseSharingBalanceOutput = new ExpenseSharingBalanceOutput(userBalances);
                output = ExpenseSharingController.createOutput(expenseSharingBalanceOutput);
                break;
            case SHOW_BALANCE, SHOW_BALANCE_FOR_A_USER:
                userBalances = expenseSharingService.showBalances();
                expenseSharingBalanceOutput = new ExpenseSharingBalanceOutput(userBalances);
                output = ExpenseSharingController.createOutput(expenseSharingBalanceOutput);
                break;
        }
        System.out.println(output);

    }


    public static String createOutput(ExpenseSharingBalanceOutput expenseSharingBalanceOutput) {
        if (expenseSharingBalanceOutput.getUserBalances() == null) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        for(UserBalance userBalance:expenseSharingBalanceOutput.getUserBalances()){
            if(userBalance.getAmount() < 0) {
                output.append(userBalance.getUserId()).append("is owed ").append(userBalance.getAmount()).append("\n");
            }
            else {
                output.append(userBalance.getUserId()).append("owes ").append(userBalance.getAmount()).append("\n");
            }
        }
        return output.toString();
    }





}
