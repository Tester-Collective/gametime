package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.*;
import enums.TransactionStatus;
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
    @Mock
    private TransactionRepository transactionRepository;

    private List<GameInCart> games = new ArrayList<>();
    private User user = new User();
    private Order order = new Order();
    private Cart cart = new Cart();
    private GameInCart gameInCart = new GameInCart();

    @BeforeEach
    void setUp() {
        Game game = new Game();
        game.setId(UUID.randomUUID());
        game.setSeller(new User());
        game.setTitle("Mock Game");
        game.setDescription("Mock Game Description");
        game.setPrice(50);
        game.setStock(5);
        cart.setGames(games);
        cart.setCustomer(user);
        cart.setCartId(UUID.randomUUID());
        gameInCart.setGame(game);
        gameInCart.setQuantity(1);
        gameInCart.setCart(cart);
        gameInCart.setGameInCartId(UUID.randomUUID());
        games.add(gameInCart);
        cart.setGames(games);
        order.setCart(cart);
        order.setOrderId(UUID.randomUUID());
    }

    @Test
    void testSave_PositiveCase() {
        Transaction transaction = new Transaction(UUID.randomUUID(), user, order);
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
        Transaction transaction = new Transaction(transactionId, user, order);
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
        Transaction transaction = new Transaction(UUID.randomUUID(), user, order);
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
        Transaction transaction = new Transaction(UUID.randomUUID(), user, order);
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