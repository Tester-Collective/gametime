package id.ac.ui.cs.advprog.gametime.model;

import enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "game_order")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    @Column(nullable = false)
    private UUID userId;

    @ManyToMany
    @JoinTable(
            name = "order_game",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> games;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int gamePrice;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private boolean orderAccess;

    @Column(nullable = false)
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

    public Order() {
    }

    public void setStatus(String status) {
        if (OrderStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }


}