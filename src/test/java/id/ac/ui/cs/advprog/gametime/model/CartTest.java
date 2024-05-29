package id.ac.ui.cs.advprog.gametime.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {
    Cart cart;

    GameInCart gameInCart;
    List<GameInCart> gameInCarts;

    Game game;

    User user;


    @BeforeEach
    void setUp() {
        this.user = new User();
        this.user.setUserID(UUID.fromString("eb558e9f-1c39-469e-8860-71af6af63bd6"));
        this.user.setUsername("Grace");

        this.game = new Game();
        this.game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd7"));
        this.game.setTitle("Esteria Chronicles");
        this.game.setPrice(200000);

        this.gameInCarts = new ArrayList<>();


        this.cart = new Cart();
        this.cart.setCartId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd8"));
        this.cart.setCustomer(this.user);
        this.cart.setGames(gameInCarts);

        this.gameInCart = new GameInCart();
        this.gameInCart.setGameInCartId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd9"));
        this.gameInCart.setCart(this.cart);
        this.gameInCart.setGame(this.game);
        this.gameInCart.setQuantity(1);
        gameInCarts.add(gameInCart);
    }

    @Test
    void testGetCartId() {
        assertEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd8"), this.cart.getCartId());
    }

    @Test
    void testGetCustomer() {
        assertEquals(this.user, this.cart.getCustomer());
    }

    @Test
    void testGetGamesFromCarts() {
        assertEquals(cart.getGames(), this.cart.getGames());
    }

    @Test
    void testAddGame() {
        this.gameInCarts.clear();
        GameInCart gameInCart = new GameInCart();
        gameInCart.setGameInCartId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd9"));
        gameInCart.setCart(this.cart);
        gameInCart.setGame(this.game);
        gameInCart.setQuantity(1);
        this.cart.addGame(gameInCart);
        assertEquals(gameInCart, this.cart.getGames().getFirst());
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd9", this.cart.getGames().getFirst().getGameInCartId().toString());
        assertEquals(this.game, this.cart.getGames().getFirst().getGame());
        assertEquals(1, this.cart.getGames().getFirst().getQuantity());
    }

    @Test
    void testAddGameDoesntExist() {
        this.gameInCarts.clear();
        GameInCart gameInCart = new GameInCart();
        this.gameInCart.setGameInCartId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd9"));
        this.gameInCart.setCart(this.cart);
        this.gameInCart.setGame(this.game);
        this.gameInCart.setQuantity(0);
        this.cart.addGame(gameInCart);
        assertNull(gameInCart.getGame());
    }

    @Test
    void testRemoveGame() {
        this.cart.removeGame(gameInCart);
        assertTrue(this.cart.getGames().isEmpty());
    }

    @Test
    void testIncreaseGameQuantity() {
        this.cart.increaseGameQuantity(gameInCart);
        assertEquals(2, this.cart.getGames().getFirst().getQuantity());
    }

    @Test
    void testDecreaseGameQuantity() {
        this.cart.decreaseGameQuantity(gameInCart);
        assertEquals(0, this.cart.getGames().getFirst().getQuantity());
    }

    @Test
    void testClearCart() {
        this.cart.clearCart();
        assertTrue(this.cart.getGames().isEmpty());
    }

    @Test
    void testValidateQuantityPositve() {
        assertDoesNotThrow(() -> this.cart.validateQuantity(0));
    }

    @Test
    void testValidateQuantityNegative() {
        assertThrows(IllegalArgumentException.class, () -> this.cart.validateQuantity(-1));
    }
}
