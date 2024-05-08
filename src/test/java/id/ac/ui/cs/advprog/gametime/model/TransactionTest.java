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
        order.setCart(cart);
        order.setOrderId(UUID.randomUUID());
    }

    @Test
    void testGetGames() {
        Transaction transaction = new Transaction(UUID.randomUUID(), user, order);
        assertEquals(games, transaction.getGames());
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
}
