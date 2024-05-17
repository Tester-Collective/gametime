package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.*;
import id.ac.ui.cs.advprog.gametime.service.CartService;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import id.ac.ui.cs.advprog.gametime.service.OrderService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private GameService gameService;

    @GetMapping("")
    public String order(Model model) {
        UUID userId = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()).getUserID();
        Order order = orderService.getOrderByUserId(userId);

        int totalPrice = 0;
        int totalQuantity = 0;
        for (GameInCart game : order.getCart().getGames()) {
            totalPrice += game.getGame().getPrice() * game.getQuantity();
            totalQuantity += game.getQuantity();
        }

        model.addAttribute("order", order);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalQuantity", totalQuantity);

        return "game/buyer/order/order_page";
    }

    @PostMapping("/checkout")
    public String checkout() {
        User user = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        Order checkOrder = orderService.getOrderByUserId(user.getUserID());
        if (checkOrder == null) {
            Order order = new Order();
            order.setCart(cartService.getCartByUser(user));
            orderService.createOrder(order);
        }
        return "redirect:/order";
    }

    @PostMapping("/pay")
    public String pay(@ModelAttribute("totalPrice") int totalPrice, @ModelAttribute("order") Order order) {
        User user = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        // TODO: Deduct the user's balance (oka's module)

        Cart cart = order.getCart();
        List<GameInCart> cartGames = cart.getGames();
        for (GameInCart cartGame : cartGames) {
            Game game = cartGame.getGame();
            gameService.decreaseStock(game, cartGame.getQuantity());
        }
        cartService.clearCart(user);

        orderService.deleteOrderById(order.getOrderId().toString());
        return "redirect:/game/buyer";
    }
}
