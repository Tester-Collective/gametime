package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Cart;
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
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest {
    private List<Game> games;
    private User user = new User();
    private Cart cart = new Cart();

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
        games.add(game);
        cart.setGames(games);
        cart.setCartId(UUID.randomUUID());
        cart.setOrderAccess(true);
        cart.setStatus("WAITING_PAYMENT");
        cart.setGameQuantity(Map.of(game, 1));
        cart.setGamePrice(Map.of(game, 50));
        cart.setCustomer(user);

    }

    @Test
    void testSave_PositiveCase() {
        Transaction transaction = new Transaction(UUID.randomUUID(), user, cart);
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
        Transaction transaction = new Transaction(transactionId, user, cart);
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
        Transaction transaction = new Transaction(UUID.randomUUID(), user, cart);
        transactions.add(transaction);
        when(transactionRepository.findByCart(cart)).thenReturn(transactions);
        assertEquals(transactions, transactionRepository.findByCart(cart));
    }
    @Test
    void testFindByOrder_NegativeCase() {
        when(transactionRepository.findByCart(cart)).thenReturn(null);
        assertNull(transactionRepository.findByCart(cart));
    }
    @Test
    void testFindByUserId_PositiveCase() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction(UUID.randomUUID(), user, cart);
        transactions.add(transaction);
        when(transactionRepository.findByUser(user)).thenReturn(transactions);
        assertEquals(transactions, transactionRepository.findByUser(user));
    }
    @Test
    void testFindByUserId_NegativeCase() {
        when(transactionRepository.findByUser(user)).thenReturn(null);
        assertNull(transactionRepository.findByUser(user));
    }
}