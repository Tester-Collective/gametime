package id.ac.ui.cs.advprog.gametime.model.state;

import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;

public interface TransactionState {
    void handle(Transaction transaction, TransactionService service);
}