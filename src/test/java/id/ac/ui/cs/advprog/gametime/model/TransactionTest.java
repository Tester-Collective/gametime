package id.ac.ui.cs.advprog.gametime.model;
import enums.TransactionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    private List<GameInCart> gamesincart = new ArrayList<>();
    private List<Game> games = new ArrayList<>();
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
        games.add(game);
        cart.setGames(gamesincart);
        cart.setCustomer(user);
        cart.setCartId(UUID.randomUUID());
        gameInCart.setGame(game);
        gameInCart.setQuantity(1);
        gameInCart.setCart(cart);
        gameInCart.setGameInCartId(UUID.randomUUID());
        gamesincart.add(gameInCart);
        cart.setGames(gamesincart);
        order.setGameQuantity(Map.of(game, 1));
        order.setCart(cart);
        order.setOrderId(UUID.randomUUID());
    }

    @Test
    void testSetTransactionId() {
        Transaction transaction = new Transaction();
        UUID transactionId = UUID.randomUUID();
        transaction.setTransactionId(transactionId);
        assertEquals(transactionId, transaction.getTransactionId());
    }
    @Test
    void testSetUser() {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        assertEquals(user, transaction.getUser());
    }
    @Test
    void testSetOrder() {
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        assertEquals(order, transaction.getOrder());
    }
    @Test
    void testSetStatus() {
        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.FAILED.getValue());
        assertEquals(TransactionStatus.FAILED.getValue(), transaction.getStatus());
    }
    @Test
    void testSetStatusFailed() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, user, order, TransactionStatus.FAILED.getValue());
        assertEquals(TransactionStatus.FAILED.getValue(), transaction.getStatus());
    }
    @Test
    void testSetStatusSuccess() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, user, order, TransactionStatus.SUCCESS.getValue());
        assertEquals(TransactionStatus.SUCCESS.getValue(), transaction.getStatus());
    }
    @Test
    void testNegativeSetStatus() {
        Transaction transaction = new Transaction();
        assertThrows(IllegalArgumentException.class, () -> transaction.setStatus("INVALID_STATUS"));
    }
    @Test
    void testSetTransactionDate() {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate("01-01-2020 00:00:00");
        assertEquals("01-01-2020 00:00:00", transaction.getTransactionDate());
    }
    @Test
    void testGetTotalPrice() {
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        assertEquals(50, transaction.getTotalPrice());
    }
    @Test
    void testConstructor() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, user, order);
        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(user, transaction.getUser());
        assertEquals(order, transaction.getOrder());
        assertEquals(TransactionStatus.FAILED.getValue(), transaction.getStatus());
    }
    @Test
    void testConstructorWithStatus() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, user, order, TransactionStatus.SUCCESS.getValue());
        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(user, transaction.getUser());
        assertEquals(order, transaction.getOrder());
        assertEquals(TransactionStatus.SUCCESS.getValue(), transaction.getStatus());
    }
    @Test
    void testGetTransactionId() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        assertEquals(transactionId, transaction.getTransactionId());
    }
    @Test
    void testGetUser() {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        assertEquals(user, transaction.getUser());
    }
    @Test
    void testGetOrder() {
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        assertEquals(order, transaction.getOrder());
    }
    @Test
    void testGetStatus() {
        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.FAILED.getValue());
        assertEquals(TransactionStatus.FAILED.getValue(), transaction.getStatus());
    }
    @Test
    void testGetTransactionDate() {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate("01-01-2020 00:00:00");
        assertEquals("01-01-2020 00:00:00", transaction.getTransactionDate());
    }
    @Test
    void testGetTransactionDateWithConstructor() {
        Transaction transaction = new Transaction();
        assertNotNull(transaction.getTransactionDate());
    }
    @Test
    void testGetTotalPriceWithEmptyOrder() {
        Transaction transaction = new Transaction();
        Order order = new Order();
        order.setGameQuantity(Map.of());
        transaction.setOrder(order);
        assertEquals(0, transaction.getTotalPrice());
    }
}
