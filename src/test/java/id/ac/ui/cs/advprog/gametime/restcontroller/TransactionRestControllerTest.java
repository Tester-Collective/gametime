package id.ac.ui.cs.advprog.gametime.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import id.ac.ui.cs.advprog.gametime.model.*;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import java.util.stream.Collectors;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionRestControllerTest {

    @InjectMocks
    private TransactionRestController transactionRestController;

    @Mock
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private User user;
    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testUser");

        transactions = new ArrayList<>();
        Order order = new Order();
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        transactions.add(transaction);

        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn(user.getUsername());
        lenient().when(userService.getLoggedInUser()).thenReturn(user);
    }

    @Test
    void shouldReturnSellerTransactions() {
        when(transactionService.findAllTransactionofSeller(user)).thenReturn(transactions);

        List<Transaction> result = transactionRestController.getAllSellerTransactions();

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> jsonTransactions = result.stream()
                .map(transaction -> {
                    try {
                        return objectMapper.writeValueAsString(transaction);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());

        List<String> expectedJsonTransactions = transactions.stream()
                .map(transaction -> {
                    try {
                        return objectMapper.writeValueAsString(transaction);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());

        assertEquals(expectedJsonTransactions, jsonTransactions);
    }


    @Test
    void shouldReturnBuyerTransactions() {
        when(transactionService.findAllTransactionofUser(user)).thenReturn(transactions);

        List<Transaction> result = transactionRestController.getAllBuyerTransactions();

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> jsonTransactions = transactions.stream()
                .map(transaction -> {
                    try {
                        return objectMapper.writeValueAsString(transaction);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());

        List<String> expectedJsonTransactions = transactions.stream()
                .map(transaction -> {
                    try {
                        return objectMapper.writeValueAsString(transaction);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());

        assertEquals(expectedJsonTransactions, jsonTransactions);
    }
}