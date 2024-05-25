package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.Order;
import id.ac.ui.cs.advprog.gametime.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order, Cart cart) {
        order.setCart(cart);
        List<GameInCart> gamesInCart = order.getCart().getGames();
        for (GameInCart gameInCart : gamesInCart) {
            order.getGameQuantity().put(gameInCart.getGame(), gameInCart.getQuantity());
        }
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderByUserId(UUID userId) {
        return orderRepository.findOrderByCart_Customer_UserID(userId);
    }

    @Override
    public void deleteOrderById(String id) {
        orderRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    public Order getLatestOrder(String userId) {
        return orderRepository.findTopByCart_Customer_UserIDOrderByOrderDateDesc(UUID.fromString(userId));
    }

    @Override
    public void setStatus(String status, Order order) {
        order.setOrderStatus(status);
        orderRepository.save(order);
    }
}
