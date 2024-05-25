package id.ac.ui.cs.advprog.gametime.model.state;

import enums.OrderStatus;
import enums.TransactionStatus;
import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;

public class FailedState implements TransactionState {
    @Override
    public void handle(Transaction transaction, TransactionService service) {
        transaction.setStatus(TransactionStatus.FAILED.getValue());
        transaction.getOrder().setOrderStatus(OrderStatus.FAILED.getValue());
        service.saveTransaction(transaction);
    }
}