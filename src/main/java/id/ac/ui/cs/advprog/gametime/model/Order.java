package id.ac.ui.cs.advprog.gametime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "order")
public class Order {
    @Id
    private UUID orderId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "cart_id",
            referencedColumnName = "cartId",
            nullable = false
    )
    private Cart cart;
}
