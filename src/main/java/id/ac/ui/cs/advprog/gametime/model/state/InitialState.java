package id.ac.ui.cs.advprog.gametime.model.state;

import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.model.state.FailedState;
import id.ac.ui.cs.advprog.gametime.model.state.SuccessState;
import id.ac.ui.cs.advprog.gametime.model.state.TransactionState;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;

public class InitialState implements TransactionState {
    @Override
    public void handle(Transaction transaction, TransactionService service) {
        if (service.hasSufficientBalance(transaction)) {
            transaction.setState(new SuccessState());
        } else {
            transaction.setState(new FailedState());
        }
        transaction.processState(service);
    }
}