package id.ac.ui.cs.advprog.gametime.model;

import enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {
    Order order1;
    Cart cart;
    Game game;
    @BeforeEach
    void setUp() {
        order1 = new Order();
        cart = new Cart();
        game = new Game();

        Map<Game, Integer> gameQuantity = new HashMap<>();
        cart.setCartId(UUID.fromString("cb4ba85c-5124-4778-9579-0d9ecbc4cf0e"));
        game.setId(UUID.fromString("f3b3b3b3-3b3b-3b3b-3b3b-3b3b3b3b3b3b"));
        game.setTitle("Overwatch");
        gameQuantity.put(game, 1);

        order1.setOrderId(UUID.fromString("56f1b45d-b577-415c-81f9-e6505a1f1f34"));
        order1.setOrderStatus(OrderStatus.WAITING_PAYMENT.getValue());
        order1.setCart(cart);
        order1.setGameQuantity(gameQuantity);
    }

    @Test
    void testGetOrderId() {
        assertEquals(UUID.fromString("56f1b45d-b577-415c-81f9-e6505a1f1f34"), order1.getOrderId());
    }

    @Test
    void testGetCart() {
        assertEquals(cart, order1.getCart());
    }

    @Test
    void testGetGameQuantity() {
        Map<Game, Integer> gameQuantity = new HashMap<>();
        gameQuantity.put(game, 1);
        assertEquals(gameQuantity, order1.getGameQuantity());
    }

    @Test
    void testGetOrderStatus() {
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order1.getOrderStatus());
    }

    @Test
    void testSetOrderId() {
        UUID orderId = UUID.fromString("56f1b45d-b577-415c-81f9-e6505a1f1f35");
        order1.setOrderId(orderId);
        assertEquals(orderId, order1.getOrderId());
    }

    @Test
    void testSetCart() {
        Cart cart = new Cart();
        cart.setCartId(UUID.fromString("cb4ba85c-5124-4778-9579-0d9ecbc4cf0f"));
        order1.setCart(cart);
        assertEquals(cart, order1.getCart());
    }

    @Test
    void testSetGameQuantity() {
        Map<Game, Integer> gameQuantity = new HashMap<>();
        gameQuantity.put(game, 2);
        order1.setGameQuantity(gameQuantity);
        assertEquals(gameQuantity, order1.getGameQuantity());
    }

    @Test
    void testSetOrderStatusFailed() {
        order1.setOrderStatus(OrderStatus.FAILED.getValue());
        assertEquals(OrderStatus.FAILED.getValue(), order1.getOrderStatus());
    }

    @Test
    void testSetOrderStatusSuccess() {
        order1.setOrderStatus(OrderStatus.SUCCESS.getValue());
        assertEquals(OrderStatus.SUCCESS.getValue(), order1.getOrderStatus());
    }

    @Test
    void testSetOrderStatusWaitingPayment() {
        order1.setOrderStatus(OrderStatus.WAITING_PAYMENT.getValue());
        assertEquals(OrderStatus.WAITING_PAYMENT.getValue(), order1.getOrderStatus());
    }

    @Test
    void testSetOrderStatusCancelled() {
        order1.setOrderStatus(OrderStatus.CANCELLED.getValue());
        assertEquals(OrderStatus.CANCELLED.getValue(), order1.getOrderStatus());
    }

    @Test
    void testOrderDate() {
        // order date is localDateTime
        order1.setOrderDate(LocalDateTime.parse("2020-05-01T00:00:00"));
        assertEquals(LocalDateTime.parse("2020-05-01T00:00:00"), order1.getOrderDate());
    }
}
