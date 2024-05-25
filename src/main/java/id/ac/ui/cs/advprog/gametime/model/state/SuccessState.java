package id.ac.ui.cs.advprog.gametime.model.state;

import enums.TransactionStatus;
import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;

public class SuccessState implements TransactionState {
    @Override
    public void handle(Transaction transaction, TransactionService service) {
        transaction.setStatus(TransactionStatus.SUCCESS.getValue());
        service.decreaseUserBalance(transaction);
        service.decreaseGameStock(transaction);
        service.updateSellerBalance(transaction);
        service.saveTransaction(transaction);
    }
}