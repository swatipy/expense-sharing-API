package org.example.controller;

import org.example.model.ExpenseSharingInput;
import org.example.model.UserBalance;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService  userBalanceService;

    Map<String, Double> userBalance = new HashMap<>();

    @PostMapping("/process-api")
     public List<UserBalance> updateBalance(@RequestBody ExpenseSharingInput input){

        List<UserBalance> balances = processCommand(input);

        return userBalanceService.updateUserBalances(balances); //update list of userbalance in database!



    }



    public List<UserBalance> processCommand(ExpenseSharingInput input) {

        List<UserBalance> userlist;

        if (input.getExpenseSharingCommand().equals("EXPENSE")) {
            addExpense(input.getPaidBy(), input.getAmountPaid(),input.getNumberOfUsers(), input.getUserIds(), input.getSplitType(), input.getValues());
            userlist=showBalances();
            return userlist;
        } else if (input.getExpenseSharingCommand().equals("SHOW")) {
            userlist=showBalances();
            return userlist;
        }

        else{
            System.out.println("Invalid command.");
            return Collections.emptyList();
        }
    }

    public void addExpense(String payerId, double amount, int numUsers, List<String> userIds, String type, List<Double> values) {


        double share = amount / numUsers;


        if (type.equals("EQUAL")) {

            for (String x : userIds) {
                if (!x.equals(payerId))

                    userBalance.put(x, userBalance.getOrDefault(x, 0.0) + share);
            }

            userBalance.put(payerId, userBalance.getOrDefault(payerId, 0.0) + share - amount);

        } else if (type.equals("EXACT")) {

            double totalShare = 0;

            for (double value : values) {
                totalShare += value;
            }
            for (int x = 0; x < userIds.size(); x++) {
                String userId = userIds.get(x);
                if (!userIds.get(x).equals(payerId))
                    userBalance.put(userId, userBalance.getOrDefault(payerId, 0.0) + values.get(x));


            }

            userBalance.put(payerId, userBalance.getOrDefault(payerId, 0.0) - (totalShare));

        } else if (type.equals("PERCENT")) {

            System.out.println("Percent mode");
            double totalShare = 0;


            for (int x = 0; x < userIds.size(); x++) {
                String userId = userIds.get(x);
                if (!userIds.get(x).equals(payerId)) {
                    userBalance.put(userId, userBalance.getOrDefault(userId, 0.0) + values.get(x) / 100 * amount);
                    totalShare += (values.get(x) / 100) * amount;
                }


            }


            userBalance.put(payerId, userBalance.getOrDefault(payerId, 0.0) + (-totalShare));
        }

    }
        public List<UserBalance> showBalances() {


        List<UserBalance> ublist = new ArrayList<>();

        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Double> entry : userBalance.entrySet()) {
            String userId = entry.getKey();
            Double balance = entry.getValue();

            ublist.add(new UserBalance(userId, balance));
            //return result.isEmpty() ? "No balances" : result.toString().trim();
        }

        return ublist;

    }


}
