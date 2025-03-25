package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name="user_balances")
public class UserBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private final String userId;
    private final Double amount;


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
}
