package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class SellerTransactionControllerTest {

    @Mock
    Model model;

    @Mock
    UserService userService;

    @InjectMocks
    SellerTransactionController sellerTransactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDisplaySellerTransactionHistoryWhenUserIsLoggedIn() {
        User seller = new User();

        when(userService.getLoggedInUser()).thenReturn(seller);

        String viewName = sellerTransactionController.transactionHistory(model);

        verify(model, times(1)).addAttribute("user", seller);
        assertEquals("game/seller/transaction/sellerTransactionHistory", viewName);
    }

    @Test
    void shouldNotDisplaySellerTransactionHistoryWhenUserIsNotLoggedIn() {
        when(userService.getLoggedInUser()).thenReturn(null);

        String viewName = sellerTransactionController.transactionHistory(model);

        verify(model, times(1)).addAttribute("user", null);
        assertEquals("game/seller/transaction/sellerTransactionHistory", viewName);
    }
}