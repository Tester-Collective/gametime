package id.ac.ui.cs.advprog.gametime.controller;

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

class SellerTransactionControllerTest {

    @Mock
    Model model;

    @Mock
    UserService userService;

    @Mock
    TransactionService transactionService;

    @InjectMocks
    SellerTransactionController sellerTransactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDisplayEmptyTransactionHistoryWhenNoTransactionsExist() {
        User seller = new User();
        when(userService.getLoggedInUser()).thenReturn(seller);
        when(transactionService.findAllTransactionofSeller(seller)).thenReturn(Collections.emptyList());

        String viewName = sellerTransactionController.transactionHistory(model);

        verify(model, times(1)).addAttribute("user", seller);
        assertEquals("game/seller/transaction/empty", viewName);
    }

    @Test
    void shouldDisplayTransactionHistoryWhenTransactionsExist() {
        User seller = new User();
        List<Transaction> transactions = List.of(new Transaction());
        when(userService.getLoggedInUser()).thenReturn(seller);
        when(transactionService.findAllTransactionofSeller(seller)).thenReturn(transactions);

        String viewName = sellerTransactionController.transactionHistory(model);

        verify(model, times(1)).addAttribute("user", seller);
        assertEquals("game/seller/transaction/sellerTransactionHistory", viewName);
    }
}