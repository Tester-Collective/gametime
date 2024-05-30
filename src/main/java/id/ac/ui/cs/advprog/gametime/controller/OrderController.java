package id.ac.ui.cs.advprog.gametime.controller;
import enums.OrderStatus;
import id.ac.ui.cs.advprog.gametime.model.*;
import id.ac.ui.cs.advprog.gametime.service.*;
import id.ac.ui.cs.advprog.gametime.service.strategy.CartStockManagementStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CartStockManagementStrategy cartStockManagementStrategy;

    @GetMapping("")
    public String order(Model model) {
        model.addAttribute("user", userService.getLoggedInUser());
        return "game/buyer/order/order";
    }

    @PostMapping("/pay")
    public String removeGameFromCart() {
        User user = userService.getLoggedInUser();

        int totalPrice = 0;
        Cart cart = cartService.getCartByUser(user);
        for (GameInCart gameInCart : cart.getGames()) {
            cartStockManagementStrategy.checkStockAvailability(gameInCart);
            totalPrice += gameInCart.getQuantity() * gameInCart.getGame().getPrice();
        }

        Order order = new Order();
        order.setCart(cart);
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT.getValue());
        order.setOrderDate(LocalDateTime.now());

        for (GameInCart gameInCart : cart.getGames()) {
            order.getGameQuantity().put(gameInCart.getGame(), gameInCart.getQuantity());
        }
        orderService.setStatus(OrderStatus.SUCCESS.getValue(), order);

        boolean hasSufficientBalance = user.getBalance() >= totalPrice;
        Transaction transaction = new Transaction(UUID.randomUUID(), user, order);
        transactionService.create(transaction);

        if (!hasSufficientBalance) {
            return "redirect:/game/buyer?alert=insufficientBalance";
        }

        cartService.clearCart(user);

        return "redirect:/game/buyer?alert=orderSuccess";
    }
}