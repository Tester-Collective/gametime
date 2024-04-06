package id.ac.ui.cs.advprog.gametime.model;
import enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private List<Game> games;
    private List<Order> orders;
    private UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        games = new ArrayList<>();
        Game game = new Game();
        game.setId(UUID.randomUUID());
        game.setSellerId(UUID.randomUUID());
        game.setTitle("Mock Game");
        game.setDescription("Mock Game Description");
        game.setPrice(50);
        game.setStock(5);
        game.setImageLink("http://example.com/mockgame.jpg");
        games.add(game);

        orders = new ArrayList<>();
        Order order = new Order(UUID.randomUUID(), userId, games, 1, 50, 5, OrderStatus.WAITING_PAYMENT.getValue());
        orders.add(order);
    }

    @Test
    void testCreateTransactionEmptyOrdersandGames() {
        this.orders.clear();
        this.games.clear();
        UUID transactionId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(transactionId, userId, games, orders);
        });
    }
    @Test
    void testCreateTransactionGamesandOrdersNotEmpty() {
        UUID transactionId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(transactionId, userId, games, orders);
        });
    }
    @Test
    void testCreateTransactionWithNonEmptyGamesAndEmptyOrders() {
        orders.clear();
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, userId, games, orders);

        assertNotNull(transaction);
        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(userId, transaction.getUserId());
        assertFalse(transaction.getGames().isEmpty());
        assertTrue(transaction.getOrders().isEmpty());
    }

    @Test
    void testCreateTransactionWithEmptyGamesAndNonEmptyOrders() {
        games.clear();
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, userId, games, orders);

        assertNotNull(transaction);
        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(userId, transaction.getUserId());
        assertTrue(transaction.getGames().isEmpty());
        assertFalse(transaction.getOrders().isEmpty());
    }
}