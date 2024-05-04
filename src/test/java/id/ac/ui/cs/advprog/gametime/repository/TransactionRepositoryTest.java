package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Order;
import id.ac.ui.cs.advprog.gametime.model.Transaction;
import id.ac.ui.cs.advprog.gametime.model.Game;
import enums.TransactionStatus;
import id.ac.ui.cs.advprog.gametime.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest {
    private List<Game> games;
    private UUID userId = UUID.randomUUID();
    private Order order = new Order();

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        games = new ArrayList<>();
        Game game = new Game();
        game.setId(UUID.randomUUID());
        game.setSeller(new User());
        game.setTitle("Mock Game");
        game.setDescription("Mock Game Description");
        game.setPrice(50);
        game.setStock(5);
        game.setImageLink("http://example.com/mockgame.jpg");
        games.add(game);
        order.setGames(games);
        order.setOrderId(UUID.randomUUID());
        order.setOrderAccess(true);
        order.setStatus("WAITING_PAYMENT");
        order.setQuantity(1);
        order.setGamePrice(50);
        order.setTotalPrice(50);
        order.setUserId(userId);
        order.setStock(5);

    }

    @Test
    void testSave_PositiveCase() {
        Transaction transaction = new Transaction(UUID.randomUUID(), userId, order);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        assertEquals(transaction, transactionRepository.save(transaction));
    }
    @Test
    void testSave_NegativeCase() {
        when(transactionRepository.save(null)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> {
            transactionRepository.save(null);
        });
    }
    @Test
    void testFindById_PositiveCase() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, userId, order);
        when(transactionRepository.findById(transactionId)).thenReturn(java.util.Optional.of(transaction));
        assertTrue(transactionRepository.findById(transactionId).isPresent());
    }
    @Test
    void testFindById_NegativeCase() {
        assertFalse(transactionRepository.findById(UUID.randomUUID()).isPresent());
    }
    @Test
    void testFindByOrder_PositiveCase() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction(UUID.randomUUID(), userId, order);
        transactions.add(transaction);
        when(transactionRepository.findByOrder(order)).thenReturn(transactions);
        assertEquals(transactions, transactionRepository.findByOrder(order));
    }
    @Test
    void testFindByOrder_NegativeCase() {
        when(transactionRepository.findByOrder(order)).thenReturn(null);
        assertNull(transactionRepository.findByOrder(order));
    }
    @Test
    void testFindByUserId_PositiveCase() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction(UUID.randomUUID(), userId, order);
        transactions.add(transaction);
        when(transactionRepository.findByUserId(userId)).thenReturn(transactions);
        assertEquals(transactions, transactionRepository.findByUserId(userId));
    }
    @Test
    void testFindByUserId_NegativeCase() {
        when(transactionRepository.findByUserId(userId)).thenReturn(null);
        assertNull(transactionRepository.findByUserId(userId));
    }
}