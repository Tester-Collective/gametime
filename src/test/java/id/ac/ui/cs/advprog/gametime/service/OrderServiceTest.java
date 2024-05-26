package id.ac.ui.cs.advprog.gametime.service;

import enums.OrderStatus;
import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.Order;
import id.ac.ui.cs.advprog.gametime.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    Order order;

    Game game1;
    Game game2;
    Cart cart;
    GameInCart gameInCart1;
    GameInCart getGameInCart2;
    List<GameInCart> cartGames;

    Map<Game, Integer> gameQuantity;



    @BeforeEach
    public void setUp() {
        game1 = new Game();
        game1.setId(UUID.fromString("635efa62-9375-454e-ab7c-7dcf17f74dd6"));
        game1.setTitle("CS:GO");
        game1.setPrice(215000);

        game2 = new Game();
        game2.setId(UUID.fromString("635efa62-9375-454e-ab7c-7dcf17f74dd7"));
        game2.setTitle("Dota 2");
        game2.setPrice(200000);

        cart = new Cart();
        cartGames = new ArrayList<>();

        gameInCart1 = new GameInCart();
        gameInCart1.setGame(game1);
        gameInCart1.setQuantity(1);

        getGameInCart2 = new GameInCart();
        getGameInCart2.setGame(game2);
        getGameInCart2.setQuantity(2);

        cartGames.add(gameInCart1);
        cartGames.add(getGameInCart2);

        cart.setGames(cartGames);

        gameQuantity = new HashMap<>();

        order = new Order();
        order.setCart(cart);
        for (GameInCart gameInCart : cart.getGames()) {
            order.getGameQuantity().put(gameInCart.getGame(), gameInCart.getQuantity());
        }
    }

    @Test
    void testSave() {
        order = new Order();
        order.setCart(cart);
        when(orderRepository.save(order)).thenReturn(order);
        orderService.save(order, cart);

        assertTrue(order.getGameQuantity().containsKey(game1));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrderByUserId() {
        UUID userId = UUID.fromString("eb558e9f-1c39-469e-8860-71af6af63bd6");
        when(orderRepository.findOrderByCart_Customer_UserID(userId)).thenReturn(order);
        Order result = orderService.getOrderByUserId(userId);
        assertTrue(result.getGameQuantity().containsKey(game1));
        verify(orderRepository, times(1)).findOrderByCart_Customer_UserID(userId);
    }

    @Test
    void testDeleteOrderById() {
        String id = "635efa62-9375-454e-ab7c-7dcf17f74dd6";
        orderService.deleteOrderById(id);
        verify(orderRepository, times(1)).deleteById(UUID.fromString(id));
    }

    @Test
    void testGetLatestOrder() {
        String userId = "eb558e9f-1c39-469e-8860-71af6af63bd6";
        when(orderRepository.findTopByCart_Customer_UserIDOrderByOrderDateDesc(UUID.fromString(userId))).thenReturn(order);
        Order result = orderService.getLatestOrder(userId);
        assertTrue(result.getGameQuantity().containsKey(game1));
        verify(orderRepository, times(1)).findTopByCart_Customer_UserIDOrderByOrderDateDesc(UUID.fromString(userId));
    }

    @Test
    void testSetOrderStatusSuccess() {
        String status = OrderStatus.SUCCESS.getValue();
        order.setOrderStatus(status);
        assertTrue(OrderStatus.contains(order.getOrderStatus()));
        orderService.setStatus(status, order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetOrderStatusFailed() {
        String status = OrderStatus.FAILED.getValue();
        order.setOrderStatus(status);
        assertTrue(OrderStatus.contains(order.getOrderStatus()));
        orderService.setStatus(status, order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetOrderStatusWaitingPayment() {
        String status = OrderStatus.WAITING_PAYMENT.getValue();
        order.setOrderStatus(status);
        assertTrue(OrderStatus.contains(order.getOrderStatus()));
        orderService.setStatus(status, order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetOrderStatusCancelled() {
        String status = OrderStatus.CANCELLED.getValue();
        order.setOrderStatus(status);
        assertTrue(OrderStatus.contains(order.getOrderStatus()));
        orderService.setStatus(status, order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrderById() {
        String id = "635efa62-9375-454e-ab7c-7dcf17f74dd6";
        when(orderRepository.findById(UUID.fromString(id))).thenReturn(java.util.Optional.ofNullable(order));
        Order result = orderService.getOrderById(id);
        assertTrue(result.getGameQuantity().containsKey(game1));
        verify(orderRepository, times(1)).findById(UUID.fromString(id));
    }

    @Test
    void testSetOrderStatus_Negative() {
        String status = "INVALID";
        order.setOrderStatus(status);
        assertFalse(OrderStatus.contains(order.getOrderStatus()));
        orderService.setStatus(status, order);
        verify(orderRepository, times(1)).save(order);
    }
}
