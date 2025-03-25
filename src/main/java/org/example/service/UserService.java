package org.example.service;

import org.example.model.UserBalance;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userBalanceRepository;

    public List<UserBalance> updateUserBalances(List<UserBalance> balances) {
        return userBalanceRepository.saveAll(balances);
    }


}
