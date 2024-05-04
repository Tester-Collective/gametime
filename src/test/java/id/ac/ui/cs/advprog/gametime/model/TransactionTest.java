package id.ac.ui.cs.advprog.gametime.model;
import enums.TransactionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private List<Game> games;
    private UUID userId = UUID.randomUUID();
    private Order order = new Order();

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
    void testGetGames() {
        Transaction transaction = new Transaction(UUID.randomUUID(), userId, order);
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
        transaction.setUserId(userId);
        assertEquals(userId, transaction.getUserId());
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
}