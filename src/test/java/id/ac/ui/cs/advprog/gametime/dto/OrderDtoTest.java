package id.ac.ui.cs.advprog.gametime.dto;

import id.ac.ui.cs.advprog.gametime.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderDtoTest {
    Game game;
    OrderDto orderDto;
    String cartId;
    Map<String, Integer> gameQuantity;
    LocalDateTime orderDate;

    @BeforeEach
    void setUp() {
        this.gameQuantity = new HashMap<>();
        this.game = new Game();
        this.orderDto = new OrderDto();
        this.cartId = "eb558e9f-1c39-460e-8860-71af6af63bd7";
        this.gameQuantity.put("eb558e9f-1c39-460e-8860-71af6af63bd7", 1);
        this.orderDate = LocalDateTime.now();
    }

    // Add your test here
    @Test
    void testGetCartId() {
        orderDto.setCartId(cartId);
        assertEquals(cartId, orderDto.getCartId());
    }

    @Test
    void testGetGameQuantity() {
        orderDto.setGameQuantity(gameQuantity);
        assertEquals(gameQuantity, orderDto.getGameQuantity());
    }

    @Test
    void testGetOrderDate() {
        orderDto.setOrderDate(orderDate);
        assertEquals(orderDate, orderDto.getOrderDate());
    }

    @Test
    void testGetOrderStatus() {
        orderDto.setOrderStatus("Pending");
        assertEquals("Pending", orderDto.getOrderStatus());
    }

    @Test
    void testSetCartId() {
        String newCartId = "eb558e9f-1c39-460e-8860-71af6af63bd8";
        orderDto.setCartId(newCartId);
        assertEquals(newCartId, orderDto.getCartId());
    }


}
