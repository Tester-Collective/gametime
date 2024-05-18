package id.ac.ui.cs.advprog.gametime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID cartId;

    @ManyToOne
    @JoinColumn(nullable = true, name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameInCart> games;

    public void addGame(GameInCart game) {
        validateQuantity(game.getQuantity());
        games.add(game);
    }

    public void removeGame(GameInCart game) {
        games.remove(game);
    }

    public void increaseGameQuantity(GameInCart game) {
        game.setQuantity(game.getQuantity() + 1);
    }

    public void decreaseGameQuantity(GameInCart game) {
        game.setQuantity(game.getQuantity() - 1);
    }

    public void clearCart() {
        games.clear();
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

    }
}