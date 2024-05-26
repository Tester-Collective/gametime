package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CartService;
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
        List<GameInCart> games = (cart.getGames() != null) ? cart.getGames() : new ArrayList<>();
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
}