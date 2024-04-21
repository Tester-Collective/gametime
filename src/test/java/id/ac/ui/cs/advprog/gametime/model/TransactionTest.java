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
    private List<Order> orders;
    private UUID userId = UUID.randomUUID();
    private UUID orderId = UUID.randomUUID();

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
    }

    @Test
    void testCreateTransactionEmptyGames() {
        this.games.clear();
        UUID transactionId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> {
            Transaction transaction = new Transaction(transactionId, userId, games, orderId, TransactionStatus.FAILED.getValue() );
        });
    }

    @Test
    void testCreateTransactionWithNonEmptyGames() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, userId, games, orderId, TransactionStatus.FAILED.getValue());

        assertNotNull(transaction);
        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(userId, transaction.getUserId());
        assertEquals(orderId, transaction.getOrderId());
        assertEquals(TransactionStatus.FAILED.getValue(), transaction.getStatus());
        assertFalse(transaction.getGames().isEmpty());
    }
}