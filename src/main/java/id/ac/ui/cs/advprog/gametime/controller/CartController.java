package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.exception.QuantityValidationException;
import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CartService;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import id.ac.ui.cs.advprog.gametime.service.UserService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String index(Model model) {
        int totalPrice = 0;
        int totalQuantity = 0;
        User customer = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        Cart cart = cartService.getCartByUser(customer);
        List<GameInCart> games = (cart != null) ? cart.getGames() : new ArrayList<>();
        games.sort(Comparator.comparing(g -> g.getGame().getTitle()));
        for (GameInCart game : games) {
            totalPrice += game.getGame().getPrice() * game.getQuantity();
            totalQuantity += game.getQuantity();
        }
        model.addAttribute("games", games);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalQuantity", totalQuantity);
        return "game/buyer/cart/index";
    }

    @PostMapping("/add/{gameId}")
    public String addGameToCart(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        Cart cart = cartService.getCartByUser(customer);
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId, cart.getCartId().toString());
        if (gameInCart != null) {
            cartService.increaseGameQuantity(customer, gameInCart);
        } else {
            gameInCart = new GameInCart();
            gameInCart.setGame(gameService.getGameById(gameId));
            gameInCart.setQuantity(1);
            gameInCart.setCart(cart);
            cartService.addGameToCart(customer, gameInCart);
        }
        return "redirect:/game/buyer";
    }

    @PostMapping("/remove/{gameId}")
    public String removeGameFromCart(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        Cart cart = cartService.getCartByUser(customer);
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId, cart.getCartId().toString());
        cartService.removeGameFromCart(customer,gameInCart);
        return "redirect:/cart";
    }

    @PostMapping("/increase/{gameId}")
    public String increaseGameQuantity(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        Cart cart = cartService.getCartByUser(customer);
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId, cart.getCartId().toString());
        cartService.increaseGameQuantity(customer,gameInCart);
        return "redirect:/cart";
    }

    @PostMapping("/decrease/{gameId}")
    public String decreaseGameQuantity(@PathVariable String gameId, Model model) {
        try {
            User customer = userService.findByUsername(SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName());
            Cart cart = cartService.getCartByUser(customer);
            GameInCart gameInCart = cartService.getGameInCartByGameId(gameId, cart.getCartId().toString());
            cartService.decreaseGameQuantity(customer, gameInCart);
        } catch (QuantityValidationException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "redirect:/cart";
        }
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        User customer = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        cartService.clearCart(customer);
        return "redirect:/cart";
    }
}