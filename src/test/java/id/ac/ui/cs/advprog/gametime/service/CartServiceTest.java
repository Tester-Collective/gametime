package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.*;
import id.ac.ui.cs.advprog.gametime.repository.CartRepository;
import id.ac.ui.cs.advprog.gametime.repository.GameInCartRepository;
import id.ac.ui.cs.advprog.gametime.service.strategy.CartStockManagementStrategy;
import id.ac.ui.cs.advprog.gametime.service.strategy.QuantityManagementStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private GameInCartRepository gameInCartRepository;

    @Mock
    private CartStockManagementStrategy cartStockManagementStrategy;

    @Mock
    private QuantityManagementStrategy quantityManagementStrategy;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Cart cart;
    private Game game;
    private GameInCart gameInCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.user = new User();
        this.user.setUserID(UUID.fromString("eb558e9f-1c39-469e-8860-71af6af63bd6"));
        this.user.setUsername("Grace");

        this.game = new Game();
        this.game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd7"));
        this.game.setTitle("Esteria Chronicles");
        this.game.setPrice(200000);

        this.cart = new Cart();
        this.cart.setCartId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd8"));
        this.cart.setCustomer(this.user);
        this.cart.setGames(new ArrayList<>());

        this.gameInCart = new GameInCart();
        this.gameInCart.setGameInCartId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd9"));
        this.gameInCart.setCart(this.cart);
        this.gameInCart.setGame(this.game);
        this.gameInCart.setQuantity(1);
    }

    @Test
    void testGetCartByUser() {
        when(cartRepository.findCartByCustomer(user)).thenReturn(cart);
        Cart foundCart = cartService.getCartByUser(user);
        assertEquals(cart, foundCart);
        verify(cartRepository, times(1)).findCartByCustomer(user);
    }

    @Test
    void testGetGameInCartByGameId() {
        when(gameInCartRepository.findGameInCartByGame_IdAndCart_CartId(game.getId(), cart.getCartId())).thenReturn(gameInCart);
        GameInCart foundGameInCart = cartService.getGameInCartByGameId(game.getId().toString(), cart.getCartId().toString());
        assertEquals(gameInCart, foundGameInCart);
        verify(gameInCartRepository, times(1)).findGameInCartByGame_IdAndCart_CartId(game.getId(), cart.getCartId());
    }

    @Test
    void testAddGameToCart() {
        when(cartRepository.findCartByCustomer(user)).thenReturn(cart);
        doNothing().when(cartStockManagementStrategy).checkStockAvailability(gameInCart);
        when(gameInCartRepository.save(gameInCart)).thenReturn(gameInCart);

        cartService.addGameToCart(user, gameInCart);

        verify(cartRepository, times(1)).findCartByCustomer(user);
        verify(cartStockManagementStrategy, times(1)).checkStockAvailability(gameInCart);
        verify(gameInCartRepository, times(1)).save(gameInCart);
        assertTrue(cart.getGames().contains(gameInCart));
    }

    @Test
    void testRemoveGameFromCart() {
        when(cartRepository.findCartByCustomer(user)).thenReturn(cart);
        doNothing().when(gameInCartRepository).delete(gameInCart);

        cartService.removeGameFromCart(user, gameInCart);

        verify(cartRepository, times(1)).findCartByCustomer(user);
        verify(gameInCartRepository, times(1)).delete(gameInCart);
        assertFalse(cart.getGames().contains(gameInCart));
    }

    @Test
    void testIncreaseGameQuantity() {
        when(cartRepository.findCartByCustomer(user)).thenReturn(cart);
        doNothing().when(cartStockManagementStrategy).checkStockAvailability(gameInCart);
        when(gameInCartRepository.save(gameInCart)).thenReturn(gameInCart);

        cartService.increaseGameQuantity(user, gameInCart);

        verify(cartRepository, times(1)).findCartByCustomer(user);
        verify(cartStockManagementStrategy, times(1)).checkStockAvailability(gameInCart);
        verify(gameInCartRepository, times(1)).save(gameInCart);
        assertEquals(2, gameInCart.getQuantity());
    }

    @Test
    void testDecreaseGameQuantity() {
        when(cartRepository.findCartByCustomer(user)).thenReturn(cart);
        doNothing().when(quantityManagementStrategy).validateQuantity(gameInCart.getQuantity());
        when(gameInCartRepository.save(gameInCart)).thenReturn(gameInCart);

        cartService.decreaseGameQuantity(user, gameInCart);

        verify(cartRepository, times(1)).findCartByCustomer(user);
        verify(quantityManagementStrategy, times(0)).validateQuantity(gameInCart.getQuantity());
        verify(gameInCartRepository, times(1)).save(gameInCart);
        assertEquals(0, gameInCart.getQuantity());
    }



    @Test
    void testClearCart() {
        when(cartRepository.findCartByCustomer(user)).thenReturn(cart);
        doNothing().when(gameInCartRepository).deleteAllByCart_CartId(cart.getCartId());

        cartService.clearCart(user);

        verify(cartRepository, times(1)).findCartByCustomer(user);
        verify(gameInCartRepository, times(1)).deleteAllByCart_CartId(cart.getCartId());
        assertTrue(cart.getGames().isEmpty());
    }

    @Test
    void testGetAllCarts() {
        List<Cart> carts = new ArrayList<>();
        carts.add(cart);
        when(cartRepository.findAll()).thenReturn(carts);

        List<Cart> foundCarts = cartService.getAllCarts();

        assertEquals(carts, foundCarts);
        verify(cartRepository, times(1)).findAll();
    }
}
