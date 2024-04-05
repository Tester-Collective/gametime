package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Order {
    private UUID orderId;
    private UUID userId;
    private UUID gameId;
    private int quantity;
    private int totalPrice;
    private int gamePrice;
    private int stock;
    private boolean isPaid;
    private boolean orderAccess;

    public Order(UUID orderId, UUID userId, UUID gameId, int quantity, int gamePrice, int stock, boolean isPaid) {
        this.orderId = orderId;
        this.userId = userId;
        this.gameId = gameId;
        this.quantity = quantity;
        this.gamePrice = gamePrice;
        this.totalPrice = this.gamePrice * this.quantity;
        this.stock = stock;
        this.isPaid = isPaid;

        if (stock == 0 || this.quantity > stock) {
            this.orderAccess = false;
        } else {
            this.orderAccess = true;
        }

        if (this.isPaid) {
            this.stock -= 1;
        }
    }
}