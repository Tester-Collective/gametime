package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Order;

import java.util.UUID;

public interface OrderService {
    public Order createOrder(Order order);
    public Order getOrderByUserId(UUID userId);
}
