import id.ac.ui.cs.advprog.gametime.controller.BuyerTransactionController;
import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BuyerTransactionControllerTest {

    @Mock
    Model model;

    @Mock
    UserService userService;

    @Mock
    TransactionService transactionService;

    @InjectMocks
    BuyerTransactionController buyerTransactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDisplayEmptyTransactionHistoryWhenNoTransactionsExist() {
        User user = new User();
        when(userService.getLoggedInUser()).thenReturn(user);
        when(transactionService.findAllTransactionofUser(user)).thenReturn(Collections.emptyList());

        String viewName = buyerTransactionController.transactionHistory(model);

        verify(model, times(1)).addAttribute("user", user);
        assertEquals("game/buyer/transaction/empty", viewName);
    }

    @Test
    void shouldDisplayTransactionHistoryWhenTransactionsExist() {
        User user = new User();
        List<Transaction> transactions = List.of(new Transaction());
        when(userService.getLoggedInUser()).thenReturn(user);
        when(transactionService.findAllTransactionofUser(user)).thenReturn(transactions);

        String viewName = buyerTransactionController.transactionHistory(model);

        verify(model, times(1)).addAttribute("user", user);
        assertEquals("game/buyer/transaction/buyerTransactionHistory", viewName);
    }
}