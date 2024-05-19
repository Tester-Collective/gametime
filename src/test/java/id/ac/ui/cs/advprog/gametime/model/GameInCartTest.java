package id.ac.ui.cs.advprog.gametime.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GameInCartTest {
    GameInCart gameInCart;
    Game game;
    Cart cart;

    @BeforeEach
    void setUp() {
        this.game = new Game();
        this.game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd7"));
        this.game.setTitle("Esteria Chronicles");
        this.game.setPrice(200000);

        this.cart = new Cart();
        this.cart.setCartId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd8"));

        this.gameInCart = new GameInCart();
        this.gameInCart.setGameInCartId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd9"));
        this.gameInCart.setCart(this.cart);
        this.gameInCart.setGame(this.game);
        this.gameInCart.setQuantity(1);
    }

    @Test
    void testGetGameInCartId() {
        assertEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd9"), this.gameInCart.getGameInCartId());
    }

    @Test
    void testGetGame() {
        assertEquals(this.game, this.gameInCart.getGame());
    }

    @Test
    void testGetCart() {
        assertEquals(this.cart, this.gameInCart.getCart());
    }

    @Test
    void testGetQuantity() {
        assertEquals(1, this.gameInCart.getQuantity());
    }

    @Test
    void testSetGameInCartId() {
        UUID newId = UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63be0");
        this.gameInCart.setGameInCartId(newId);
        assertEquals(newId, this.gameInCart.getGameInCartId());
    }

    @Test
    void testSetGame() {
        Game newGame = new Game();
        newGame.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63be1"));
        newGame.setTitle("New Game");
        newGame.setPrice(250000);
        this.gameInCart.setGame(newGame);
        assertEquals(newGame, this.gameInCart.getGame());
    }

    @Test
    void testSetCart() {
        Cart newCart = new Cart();
        newCart.setCartId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63be2"));
        this.gameInCart.setCart(newCart);
        assertEquals(newCart, this.gameInCart.getCart());
    }

    @Test
    void testSetQuantity() {
        this.gameInCart.setQuantity(5);
        assertEquals(5, this.gameInCart.getQuantity());
    }

    @Test
    void testIncreaseQuantity() {
        this.gameInCart.setQuantity(1);
        this.gameInCart.setQuantity(this.gameInCart.getQuantity() + 1);
        assertEquals(2, this.gameInCart.getQuantity());
    }

    @Test
    void testDecreaseQuantity() {
        this.gameInCart.setQuantity(1);
        this.gameInCart.setQuantity(this.gameInCart.getQuantity() - 1);
        assertEquals(0, this.gameInCart.getQuantity());
    }
}
