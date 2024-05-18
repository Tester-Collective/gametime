package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.Order;

import java.util.UUID;

public interface OrderService {
    public Order getOrderByUserId(UUID userId);
    public void deleteOrderById(String id);
    public Order getOrderById(String id);
    public Order save(Order order, Cart cart);
    public Order getLatestOrder(String userId);
    public void setStatus(String status, Order order);
}
