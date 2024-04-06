package id.ac.ui.cs.advprog.gametime.model;

import enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class Order {
    private UUID orderId;
    private UUID userId;
    private List<Game> games;
    private int quantity;
    private int totalPrice;
    private int gamePrice;
    private int stock;
    private boolean orderAccess;
    private String status;

    public Order(UUID orderId, UUID userId, List<Game> games, int quantity, int gamePrice, int stock) {
        this.orderId = orderId;
        this.userId = userId;
        this.quantity = quantity;
        this.gamePrice = gamePrice;
        this.totalPrice = this.gamePrice * this.quantity;
        this.stock = stock;
        this.status = OrderStatus.WAITING_PAYMENT.getValue();

        if (games.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.games = games;
            if (stock == 0 || this.quantity > stock) {
                this.orderAccess = false;
            } else {
                this.orderAccess = true;
            }
        }
    }
    public Order(UUID orderId, UUID userId, List<Game> games, int quantity, int gamePrice, int stock, String status) {
        this(orderId, userId, games, quantity, gamePrice, stock);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (OrderStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }


}