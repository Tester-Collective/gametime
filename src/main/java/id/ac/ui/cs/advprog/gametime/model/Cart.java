package id.ac.ui.cs.advprog.gametime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "game_order")
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID cartId;

    @ManyToOne
    @JoinColumn(nullable = true, name = "customer_id")
    private User customer;

    @ManyToMany
    @JoinTable(
            name = "cart_game",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private List<Game> games;

    @Column(nullable = false)
    private boolean orderAccess;

    @Column(nullable = false)
    private String status;

    @ElementCollection
    private Map<Game, Integer> gameQuantity;

    @ElementCollection
    private Map<Game, Integer> gamePrice;

    public Cart(UUID cartId, User customer, List<Game> games, Map<Game, Integer> gameQuantity, Map<Game, Integer> gamePrice) {
        this.cartId = cartId;
        this.customer = customer;
        this.games = games;
        this.gameQuantity = gameQuantity;
        this.gamePrice = gamePrice;
    }

    public Cart() {
    }


}