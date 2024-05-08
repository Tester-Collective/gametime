package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.Order;
import id.ac.ui.cs.advprog.gametime.service.OrderService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;

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
        UUID userId = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()).getUserID();
        Order order = orderService.getOrderByUserId(userId);
        orderService.createOrder(order);

        return "redirect:/order";
    }
}
