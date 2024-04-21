package id.ac.ui.cs.advprog.gametime.model;

import enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private List<Game> games;

    @BeforeEach
    void setUp() {
        this.games = new ArrayList<>();
        Game game = new Game();
        game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        // set the game's seller into a mock User
        game.setSeller(new User());
        game.setTitle("game");
        game.setDescription("description");
        game.setPrice(50);
        game.setStock(5);
        game.setImageLink("http://example.com/mockgame.jpg");
        this.games.add(game);
    }

    @Test
    void testDefaultOrderSuccess() {
        Order order = new Order(UUID.fromString("13652556-012a-4c07-b546-54eb1396d79b"),
                UUID.fromString("a2c62328-4a37-4664-83c7-f32db8620154"), this.games,
                1, this.games.getFirst().getPrice(), this.games.getFirst().getStock());

        assertSame(this.games, order.getGames());
        assertEquals(1, order.getGames().size());
        assertEquals("game", order.getGames().getFirst().getTitle());

        assertEquals(UUID.fromString("13652556-012a-4c07-b546-54eb1396d79b"), order.getOrderId());
        assertEquals(UUID.fromString("a2c62328-4a37-4664-83c7-f32db8620154"), order.getUserId());
        assertEquals(1, order.getQuantity());
        assertEquals(50, order.getGamePrice());
        assertEquals(50, order.getTotalPrice());
        assertEquals(5, order.getStock());
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order.getStatus());
    }

    @Test
    void testOrderSuccessStatus() {
        Order order = new Order(UUID.fromString("13652556-012a-4c07-b546-54eb1396d79b"),
                UUID.fromString("a2c62328-4a37-4664-83c7-f32db8620154"), this.games,
                1, this.games.getFirst().getPrice(), this.games.getFirst().getStock(),
                OrderStatus.SUCCESS.getValue());

        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order(UUID.fromString("13652556-012a-4c07-b546-54eb1396d79b"),
                    UUID.fromString("a2c62328-4a37-4664-83c7-f32db8620154"), this.games,
                    1, this.games.getFirst().getPrice(), this.games.getFirst().getStock(),
                    "MEOW");
        });
    }

    @Test
    void testSetStatusToCancelled() {
        Order order = new Order(UUID.fromString("13652556-012a-4c07-b546-54eb1396d79b"),
                UUID.fromString("a2c62328-4a37-4664-83c7-f32db8620154"), this.games,
                1, this.games.getFirst().getPrice(), this.games.getFirst().getStock());

        order.setStatus(OrderStatus.CANCELLED.getValue());
        assertEquals(OrderStatus.CANCELLED.getValue(), order.getStatus());
    }

    @Test
    void testStockIsEmpty() {
        this.games.getFirst().setStock(0);
        Order order = new Order(UUID.fromString("13652556-012a-4c07-b546-54eb1396d79b"),
                UUID.fromString("a2c62328-4a37-4664-83c7-f32db8620154"), this.games,
                1, this.games.getFirst().getPrice(), this.games.getFirst().getStock());

        assertFalse(order.isOrderAccess());
    }

    @Test
    void testQuantityGreaterThanStock() {
        this.games.getFirst().setStock(1);
        Order order = new Order(UUID.fromString("13652556-012a-4c07-b546-54eb1396d79b"),
                UUID.fromString("a2c62328-4a37-4664-83c7-f32db8620154"), this.games,
                2, this.games.getFirst().getPrice(), this.games.getFirst().getStock());

        assertFalse(order.isOrderAccess());
    }

}
