package id.ac.ui.cs.advprog.gametime.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

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
    void testIndexWithNonNullGames() {
        List<GameInCart> gamesList = new ArrayList<>();
        gamesList.add(gameInCart);

        cart.setGames(gamesList);

        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        String view = cartController.index(model);

        verify(userService).findByUsername(user.getUsername());
        verify(cartService).getCartByUser(user);
        verify(model).addAttribute("games", gamesList);
        verify(model).addAttribute("totalPrice", 100); // Assuming game price is 100 and quantity is 1
        verify(model).addAttribute("totalQuantity", 1);
        assertEquals("game/buyer/cart/index", view);
    }

    @Test
    void testIndexWithNullGamesList() {
        cart.setGames(null);

        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        String view = cartController.index(model);

        verify(userService).findByUsername(user.getUsername());
        verify(cartService).getCartByUser(user);
        verify(model).addAttribute("games", new ArrayList<>());
        verify(model).addAttribute("totalPrice", 0);
        verify(model).addAttribute("totalQuantity", 0);
        assertEquals("game/buyer/cart/index", view);
    }

    @Test
    void testIndexWithEmptyGamesList() {
        cart.setGames(new ArrayList<>());

        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        String view = cartController.index(model);

        verify(userService).findByUsername(user.getUsername());
        verify(cartService).getCartByUser(user);
        verify(model).addAttribute("games", new ArrayList<>());
        verify(model).addAttribute("totalPrice", 0);
        verify(model).addAttribute("totalQuantity", 0);
        assertEquals("game/buyer/cart/index", view);
    }

    @Test
    void testIndexWithSortedGamesList() {
        List<GameInCart> gamesList = new ArrayList<>();
        GameInCart gameInCart2 = new GameInCart();
        gameInCart2.setGame(game);
        gameInCart2.setQuantity(1);
        gameInCart2.setCart(cart);
        game.setTitle("Another Test Game");
        gamesList.add(gameInCart2);
        gamesList.add(gameInCart);

        cart.setGames(gamesList);

        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        String view = cartController.index(model);

        verify(userService).findByUsername(user.getUsername());
        verify(cartService).getCartByUser(user);
        verify(model).addAttribute("games", gamesList);
        verify(model).addAttribute("totalPrice", 200); // Assuming game price is 100 and quantity is 1
        verify(model).addAttribute("totalQuantity", 2);
        assertEquals("game/buyer/cart/index", view);
    }
}
