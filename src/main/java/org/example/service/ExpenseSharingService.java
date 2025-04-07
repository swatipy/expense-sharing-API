package org.example.service;

import org.example.model.Expense;
import org.example.model.ExpenseSharingInput;
import org.example.model.UserBalance;
import org.example.model.UserBalanceInterface;
import org.example.repository.ExpenseSharingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ExpenseSharingService {

    Map<String, Double> userBalanceMap = new HashMap<>();

    @Autowired
    private ExpenseSharingRepository expenseSharingRepository;

//    public List<UserBalance> updateUserBalances(List<UserBalance> balances) {
//        UserBalanceInterface userBalance = balances.get(0);
//        ExpenseSharingInput expenseSharingInput = (ExpenseSharingInput) userBalance;
//        return userBalanceRepository.saveAll(balances);
//    }

    public List<UserBalance> addExpense(Expense expense) {


            switch (expense.getSplitType()) {
                case "EQUAL":
                    this.addExpenseForEqualSplitType(expense);
                    break;
                case "EXACT":
                    this.addExpenseForExactSplitType(expense);
                    break;
                case "PERCENT":
                    this.addExpenseForPercentSplitType(expense);
                    break;
            }

            return this.showBalances();

        }

        private void addExpenseForEqualSplitType(Expense expense) {
            double share = expense.getAmountPaid() / expense.getUserIds().size();
            double paidByUserShare = 0.0;

            // u1 1000  u2, u3
            for (String userId : expense.getUserIds()) {

                // Ignore the paidBy UserId
                if (userId.equals(expense.getPaidBy())) {
                    paidByUserShare = share;
                } else {
                    Optional<UserBalance> userBalanceOpt = expenseSharingRepository.findByUserId(userId);
                   // double existingUserBalance = userBalanceOpt.map(UserBalance::getAmount).orElse(0.0);

                    UserBalance userBalance = userBalanceOpt.orElse(new UserBalance(userId, 0.0));
                    userBalance.setAmount(userBalance.getAmount() + share);
                    expenseSharingRepository.save(userBalance);
                  //  double existingUserBalance = userBalanceMap.getOrDefault(userId, 0.0);
                   // userBalanceMap.put(userId, existingUserBalance + share);
//                   Optional<UserBalance> userBalance =   userBalanceRepository.findByUserId(userId);
//                    double existingUserBalance= userBalance.get().getAmount();
//                    userBalanceRepository.save(userId, existingUserBalance + share);
                }
            }
            // Add expense for paidBy UserId
            Optional<UserBalance> payerBalanceOpt = expenseSharingRepository.findByUserId(expense.getPaidBy());
            UserBalance payerBalance = payerBalanceOpt.orElse(new UserBalance(expense.getPaidBy(), 0.0));
            payerBalance.setAmount(payerBalance.getAmount() + share);
            expenseSharingRepository.save(payerBalance);

        }

        public void addExpenseForExactSplitType(Expense expense) {
            double paidByUserShare = 0.0;
            //u1  900  u2 u3  700 200

            for (int index = 0; index < expense.getUserIds().size(); index++) {

                String userId = expense.getUserIds().get(index);

                //ignore the paidby userid
                if (userId.equals(expense.getPaidBy())) {
                    paidByUserShare = expense.getValues().get(index);
                } else {
                    double existingUserBalance = userBalanceMap.getOrDefault(userId, 0.0);
                    userBalanceMap.put(userId, existingUserBalance + expense.getValues().get(index));

                }
                double paidByUserExistingBalance = userBalanceMap.getOrDefault(expense.getPaidBy(), 0.0);
                userBalanceMap.put(expense.getPaidBy(), paidByUserExistingBalance + paidByUserShare);

            }
        }

        public void addExpenseForPercentSplitType(Expense expense) {

            //u1 1000 u1 u2 u3  40 40 20

            double paidByUserShare = 0.0;
            for (int index = 0; index < expense.getUserIds().size(); index++) {
                String userId = expense.getUserIds().get(index);

                //ignore paidBy userid
                if (expense.getUserIds().get(index).equals(expense.getPaidBy())) {
                    paidByUserShare = expense.getValues().get(index) * expense.getAmountPaid() / 100;

                } else {
                    double existingUserBalance = userBalanceMap.getOrDefault(userId, 0.0);
                    userBalanceMap.put(userId, existingUserBalance + expense.getValues().get(index) * expense.getAmountPaid() / 100);
                }

            }
            double paidByUserExistingBalance = userBalanceMap.getOrDefault(expense.getPaidBy(), 0.0);
            userBalanceMap.put(expense.getPaidBy(), paidByUserExistingBalance + paidByUserShare);

        }

        public List<UserBalance> showBalances() {

            List<UserBalance> ublist = new ArrayList<>();

            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, Double> entry : userBalanceMap.entrySet()) {
                String userId = entry.getKey();
                Double balance = entry.getValue();

                ublist.add(new UserBalance(userId, balance));
            }

            return ublist;

        }
    }


