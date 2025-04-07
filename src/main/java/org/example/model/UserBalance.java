package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name="user_balances")
public class UserBalance implements UserBalanceInterface {

    @Id
    private String userId;

    @Column
    private Double amount;

   public UserBalance(){

   }
    public UserBalance(String userId, Double amount){
       this.userId=userId;
       this.amount=amount;
    }
    public Double getAmount() {
        return amount;
    }

    public String getUserId() {
        return userId;
    }
    public void setAmount(double amount){
        this.amount=amount;
    }
}

