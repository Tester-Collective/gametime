package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Order;
import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.GameRepository;
import id.ac.ui.cs.advprog.gametime.repository.TransactionRepository;
import id.ac.ui.cs.advprog.gametime.service.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private User user;
    private User seller;
    private Transaction transaction;
    private UUID transactionId;
    private Game game;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setBalance(100);
        user.setUserID(UUID.randomUUID());
        user.setEmail("test@test.com");
        user.setPassword("test");
        user.setUsername("test");

        seller = new User();
        seller.setBalance(100);
        seller.setUserID(UUID.randomUUID());
        seller.setEmail("test2@test.com");
        seller.setPassword("test2");
        seller.setUsername("test2");

        game = new Game();
        game.setId(UUID.randomUUID());
        game.setSeller(seller);
        game.setTitle("Mock Game");
        game.setDescription("Mock Game Description");
        game.setPrice(50);
        game.setStock(5);

        order = new Order();
        order.setGameQuantity(Map.of(game, 2));
        order.setOrderId(UUID.randomUUID());

        transactionId = UUID.randomUUID();
        transaction = new Transaction(transactionId, user, order);
    }


    @Test
    void testGetTransaction() {
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        Transaction foundTransaction = transactionService.get(transactionId);

        assertNotNull(foundTransaction);
        assertEquals(transaction.getTransactionId(), foundTransaction.getTransactionId());
        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    void testNegativeGetTransaction() {
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        Transaction foundTransaction = transactionService.get(transactionId);

        assertNull(foundTransaction);
        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    void testUpdateStatus() {
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction updatedTransaction = transactionService.updateStatus(transactionId, "SUCCESS");

        assertNotNull(updatedTransaction);
        assertEquals("SUCCESS", updatedTransaction.getStatus());
        verify(transactionRepository, times(1)).findById(transactionId);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testNegativeUpdateStatus() {
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        Transaction updatedTransaction = transactionService.updateStatus(transactionId, "SUCCESS");

        assertNull(updatedTransaction);
        verify(transactionRepository, times(1)).findById(transactionId);
        verify(transactionRepository, times(0)).save(transaction);
    }

    @Test
    void testFindAllTransactionOfUser() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        when(transactionRepository.findByUser(user)).thenReturn(transactionList);

        List<Transaction> foundTransactions = transactionService.findAllTransactionofUser(user);

        assertNotNull(foundTransactions);
        assertFalse(foundTransactions.isEmpty());
        assertEquals(1, foundTransactions.size());
        verify(transactionRepository, times(1)).findByUser(user);
    }

    @Test
    void testNegativeFindAllTransactionOfUser() {
        when(transactionRepository.findByUser(user)).thenReturn(Collections.emptyList());

        List<Transaction> foundTransactions = transactionService.findAllTransactionofUser(user);

        assertNotNull(foundTransactions);
        assertTrue(foundTransactions.isEmpty());
        verify(transactionRepository, times(1)).findByUser(user);
    }

    @Test
    void testFindAllTransactionOfSeller() {
        List<Game> games = Collections.singletonList(game);
        when(gameRepository.findGamesBySeller(seller)).thenReturn(games);

        transaction.setOrder(order);
        List<Transaction> transactions = Collections.singletonList(transaction);
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> foundTransactions = transactionService.findAllTransactionofSeller(seller);

        assertNotNull(foundTransactions);
        assertFalse(foundTransactions.isEmpty());
        assertEquals(1, foundTransactions.size());
        verify(gameRepository, times(1)).findGamesBySeller(seller);
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testNegativeFindAllTransactionOfSeller() {
        List<Game> games = Collections.singletonList(game);
        when(gameRepository.findGamesBySeller(seller)).thenReturn(games);

        transaction.setOrder(order);
        List<Transaction> transactions = Collections.singletonList(transaction);
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> foundTransactions = transactionService.findAllTransactionofSeller(new User());

        assertNotNull(foundTransactions);
        assertTrue(foundTransactions.isEmpty());
        verify(gameRepository, times(1)).findGamesBySeller(any(User.class));
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testFindAllTransactionOfSellerWithMatchingGame() {
        List<Game> games = Collections.singletonList(game);
        when(gameRepository.findGamesBySeller(seller)).thenReturn(games);

        transaction.setOrder(order);
        List<Transaction> transactions = Collections.singletonList(transaction);
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> foundTransactions = transactionService.findAllTransactionofSeller(seller);

        assertNotNull(foundTransactions);
        assertFalse(foundTransactions.isEmpty());
        assertEquals(1, foundTransactions.size());
        verify(gameRepository, times(1)).findGamesBySeller(seller);
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testPositiveHasSufficientBalance() {
        user.setBalance(100);
        transaction.setUser(user);
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        boolean hasSufficientBalance = transactionService.hasSufficientBalance(transaction);

        assertTrue(hasSufficientBalance);
    }

    @Test
    void testNegativeHasSufficientBalance() {
        user.setBalance(40);  // Insufficient balance
        transaction.setUser(user);
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        boolean hasSufficientBalance = transactionService.hasSufficientBalance(transaction);

        assertFalse(hasSufficientBalance);
    }

}
