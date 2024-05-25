package id.ac.ui.cs.advprog.gametime.controller;

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
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

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
    void setUp() {
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
    void testIndex() {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        String view = cartController.index(model);

        verify(userService).findByUsername(user.getUsername());
        verify(cartService).getCartByUser(user);
        verify(model).addAttribute(eq("games"), anyList());
        verify(model).addAttribute("totalPrice", 0);
        verify(model).addAttribute("totalQuantity", 0);
        assertEquals("game/buyer/cart/index", view);
    }
}
