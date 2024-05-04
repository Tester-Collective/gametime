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

    private List<Game> games;
    private User user = new User();
    private Cart cart = new Cart();

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
    void testGetGames() {
        Transaction transaction = new Transaction(UUID.randomUUID(), user, cart);
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
    void testSetUserId() {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        assertEquals(user, transaction.getUser());
    }
    @Test
    void testSetOrder() {
        Transaction transaction = new Transaction();
        transaction.setCart(cart);
        assertEquals(cart, transaction.getCart());
    }
    @Test
    void testSetStatus() {
        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.FAILED.getValue());
        assertEquals(TransactionStatus.FAILED.getValue(), transaction.getStatus());
    }
}
