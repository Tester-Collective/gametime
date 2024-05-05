package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CartService;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import id.ac.ui.cs.advprog.gametime.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/game/buyer/cart")
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
        User customer = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        Cart cart = cartService.getCartByUser(customer);
        List<GameInCart> games = (cart != null) ? cart.getGames() : new ArrayList<>();
        for (GameInCart game : games) {
            totalPrice += game.getGame().getPrice() * game.getQuantity();
        }
        model.addAttribute("games", games);
        model.addAttribute("totalPrice", totalPrice);
        return "game/buyer/cart/index";
    }

    @PostMapping("/add/{gameId}")
    public String addGameToCart(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        Cart cart = cartService.getCartByUser(customer);
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId);
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
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId);
        cartService.removeGameFromCart(customer,gameInCart);
        return "redirect:/cart";
    }

    @PostMapping("/increase/{gameId}")
    public String increaseGameQuantity(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId);
        cartService.increaseGameQuantity(customer,gameInCart);
        return "redirect:/cart";
    }

    @PostMapping("/decrease/{gameId}")
    public String decreaseGameQuantity(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId);
        cartService.decreaseGameQuantity(customer,gameInCart);
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
