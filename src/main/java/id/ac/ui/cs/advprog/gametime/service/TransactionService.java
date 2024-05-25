package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.model.User;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Transaction create(Transaction transaction);
    Transaction get(UUID id);
    public Transaction updateStatus(UUID id, String status);
    public List<Transaction> findAllTransactionofSeller(User seller);
    public List<Transaction> findAllTransactionofUser(User user);
    public boolean hasSufficientBalance(Transaction transaction);
    public void decreaseUserBalance(Transaction transaction);
    public void decreaseGameStock(Transaction transaction);
    public void saveTransaction(Transaction transaction);
    public void updateSellerBalance(Transaction transaction);

}
