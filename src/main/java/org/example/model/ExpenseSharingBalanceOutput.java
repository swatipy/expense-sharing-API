package org.example.model;

import java.util.List;

public class ExpenseSharingBalanceOutput {


    List<UserBalance> userBalances;

    public ExpenseSharingBalanceOutput(List<UserBalance> userBalances) {
        this.userBalances = userBalances;
    }

    public List<UserBalance> getUserBalances() {
        return userBalances;
    }

}
