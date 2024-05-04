package id.ac.ui.cs.advprog.gametime.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "game_in_cart")
@Getter
@Setter
public class GameInCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID gameInCartId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(nullable = false, name = "cart_id")
    private Cart cart;

    @Column(nullable = false)
    private int quantity;
}
