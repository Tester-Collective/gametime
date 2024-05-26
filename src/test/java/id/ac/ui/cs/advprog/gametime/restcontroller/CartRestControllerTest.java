package id.ac.ui.cs.advprog.gametime.restcontroller;

import id.ac.ui.cs.advprog.gametime.controller.CartController;
import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CartService;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartRestControllerTest {

    @InjectMocks
    private CartRestController cartRestController;

    @Mock
    private CartService cartService;

    @Mock
    private GameService gameService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private User user;
    private Cart cart;
    private Game game;
    private GameInCart gameInCart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testUser");

        cart = new Cart();
        cart.setCartId(UUID.randomUUID());
        cart.setCustomer(user);
        cart.setGames(new ArrayList<>());

        game = new Game();
        game.setId(UUID.randomUUID());
        game.setTitle("Test Game");
        game.setPrice(100);

        gameInCart = new GameInCart();
        gameInCart.setGame(game);
        gameInCart.setQuantity(1);
        gameInCart.setCart(cart);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getUsername());
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testAddGameToCart() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);
        when(cartService.getGameInCartByGameId(anyString(), anyString())).thenReturn(null);
        when(gameService.getGameById(anyString())).thenReturn(game);

        ResponseEntity<?> response = cartRestController.addGameToCart(game.getId().toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testIncreaseGameQuantity() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);
        when(cartService.getGameInCartByGameId(anyString(), anyString())).thenReturn(gameInCart);

        ResponseEntity<?> response = cartRestController.increaseGameQuantity(game.getId().toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDecreaseGameQuantity() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);
        when(cartService.getGameInCartByGameId(anyString(), anyString())).thenReturn(gameInCart);

        ResponseEntity<?> response = cartRestController.decreaseGameQuantity(game.getId().toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveGameFromCart() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);
        when(cartService.getGameInCartByGameId(anyString(), anyString())).thenReturn(gameInCart);

        ResponseEntity<?> response = cartRestController.removeGameFromCart(game.getId().toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetGameQuantity() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);
        when(cartService.getGameInCartByGameId(anyString(), anyString())).thenReturn(gameInCart);

        Integer response = cartRestController.getGameQuantity(game.getId().toString());
        assertEquals(1, response);
    }

    @Test
    public void testGetGameQuantityWhenGameInCartIsNull() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);
        when(cartService.getGameInCartByGameId(anyString(), anyString())).thenReturn(null);

        Integer response = cartRestController.getGameQuantity(game.getId().toString());
        assertEquals(0, response);
    }

    @Test
    public void testGetTotalPrice() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        Integer response = cartRestController.getTotalPrice();
        assertEquals(0, response);
    }

    @Test
    public void testGetTotalQuantity() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        Integer response = cartRestController.getTotalQuantity();
        assertEquals(0, response);
    }

    @Test
    public void testClearCart() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<?> response = cartRestController.clearCart();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void addGameToCartIfGameInCartIsNotNull() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);
        when(cartService.getGameInCartByGameId(anyString(), anyString())).thenReturn(gameInCart);

        ResponseEntity<?> response = cartRestController.addGameToCart(game.getId().toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void increaseGameQuantityIfGameInCartIsNull() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);
        when(cartService.getGameInCartByGameId(anyString(), anyString())).thenReturn(null);

        ResponseEntity<?> response = cartRestController.increaseGameQuantity(game.getId().toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void decreaseGameQuantityIfGameInCartIsNull() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);
        when(cartService.getGameInCartByGameId(anyString(), anyString())).thenReturn(null);

        ResponseEntity<?> response = cartRestController.decreaseGameQuantity(game.getId().toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void removeGameFromCartIfGameInCartIsNull() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);
        when(cartService.getGameInCartByGameId(anyString(), anyString())).thenReturn(null);

        ResponseEntity<?> response = cartRestController.removeGameFromCart(game.getId().toString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
