package id.ac.ui.cs.advprog.gametime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    @ManyToOne
    @JoinColumn(
            name = "cart_id",
            referencedColumnName = "cartId",
            nullable = false
    )
    private Cart cart;

    @ElementCollection
    private Map<Game, Integer> gameQuantity = new HashMap<>();

    private LocalDateTime orderDate;

    private String orderStatus;
}
