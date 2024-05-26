package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;

class BuyerTransactionControllerTest {

    @Mock
    Model model;

    @Mock
    UserService userService;

    @InjectMocks
    BuyerTransactionController buyerTransactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDisplayTransactionHistoryWhenUserIsLoggedIn() {
        User user = new User();

        when(userService.getLoggedInUser()).thenReturn(user);

        buyerTransactionController.transactionHistory(model);

        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    void shouldNotDisplayTransactionHistoryWhenUserIsNotLoggedIn() {
        when(userService.getLoggedInUser()).thenReturn(null);

        buyerTransactionController.transactionHistory(model);

        verify(model, times(1)).addAttribute("user", null);
    }
}