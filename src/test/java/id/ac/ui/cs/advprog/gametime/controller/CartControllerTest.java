package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CartService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class CartControllerTest {

    @Mock
    Model model;


    @Mock
    CartService cartService;

    @Mock
    UserService userService;

    @InjectMocks
    CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDisplayCartWhenGamesArePresent() {
        User user = new User();
        Cart cart = new Cart();
        cart.setCustomer(user);
        GameInCart gameInCart = new GameInCart();
        gameInCart.setGame(new Game());
        gameInCart.setQuantity(1);
        List<GameInCart> games = new ArrayList<>();
        games.add(gameInCart);
        cart.setGames(games);

        when(userService.getLoggedInUser()).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        cartController.index(model);

        verify(model, times(1)).addAttribute("games", games);
        verify(model, times(1)).addAttribute("totalPrice", gameInCart.getGame().getPrice());
        verify(model, times(1)).addAttribute("totalQuantity", gameInCart.getQuantity());
        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    void shouldDisplayEmptyCartWhenNoGamesArePresent() {
        User user = new User();
        Cart cart = new Cart();
        cart.setCustomer(user);
        cart.setGames(new ArrayList<>());

        when(userService.getLoggedInUser()).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        cartController.index(model);

        verify(model, times(1)).addAttribute("games", cart.getGames());
        verify(model, times(1)).addAttribute("totalPrice", 0);
        verify(model, times(1)).addAttribute("totalQuantity", 0);
        verify(model, times(1)).addAttribute("user", user);
    }
}