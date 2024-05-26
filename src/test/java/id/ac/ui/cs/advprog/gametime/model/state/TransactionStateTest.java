package id.ac.ui.cs.advprog.gametime.model.state;

import enums.OrderStatus;
import enums.TransactionStatus;
import id.ac.ui.cs.advprog.gametime.model.Order;
import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionStateTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldTransitionToSuccessStateWhenSufficientBalance() {
        Transaction transaction = new Transaction();
        TransactionService service = mock(TransactionService.class);

        when(service.hasSufficientBalance(transaction)).thenReturn(true);

        new InitialState().handle(transaction, service);

        assertInstanceOf(SuccessState.class, transaction.getState());
    }

    @Test
    void shouldTransitionToFailedStateWhenInsufficientBalance() {
        Transaction transaction = new Transaction();
        Order mockOrder = mock(Order.class);
        transaction.setOrder(mockOrder);
        TransactionService service = mock(TransactionService.class);

        when(service.hasSufficientBalance(transaction)).thenReturn(false);

        new InitialState().handle(transaction, service);

        assertInstanceOf(FailedState.class, transaction.getState());
        verify(mockOrder, times(1)).setOrderStatus(OrderStatus.FAILED.getValue());
        assertEquals(transaction.getStatus(), TransactionStatus.FAILED.getValue());
    }
}