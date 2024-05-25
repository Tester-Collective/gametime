package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Order;
import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.TransactionRepository;
import id.ac.ui.cs.advprog.gametime.repository.GameRepository;
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
    private Transaction transaction;
    private UUID transactionId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        transactionId = UUID.randomUUID();
        transaction = new Transaction(transactionId, user, new Order());
    }

    @Test
    void testCreateTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction createdTransaction = transactionService.create(transaction);

        assertNotNull(createdTransaction);
        assertEquals(transaction.getTransactionId(), createdTransaction.getTransactionId());
        verify(transactionRepository, times(1)).save(transaction);
    }
    @Test
    void testNegativeCreateTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(null);

        Transaction createdTransaction = transactionService.create(transaction);

        assertNull(createdTransaction);
        verify(transactionRepository, times(1)).save(transaction);
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
        when(transactionRepository.findById(transaction.getTransactionId())).thenReturn(Optional.empty());
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
    void testNegativeUpdateStatus(){
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

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
        User seller = new User();
        Game game = new Game();
        game.setSeller(seller);
        List<Game> games = Collections.singletonList(game);
        when(gameRepository.findGamesBySeller(seller)).thenReturn(games);

        Order order = new Order();
        order.setGameQuantity(Map.of(game, 1));
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
        User seller = new User();
        Game game = new Game();
        game.setSeller(seller);
        List<Game> games = Collections.singletonList(game);
        when(gameRepository.findGamesBySeller(seller)).thenReturn(games);

        Order order = new Order();
        order.setGameQuantity(Map.of(game, 1));
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
        User seller = new User();
        Game game = new Game();
        game.setSeller(seller);
        game.setId(UUID.randomUUID());
        List<Game> games = Collections.singletonList(game);
        when(gameRepository.findGamesBySeller(seller)).thenReturn(games);

        Order order = new Order();
        order.setGameQuantity(Map.of(game, 1));
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

}
