package id.ac.ui.cs.advprog.gametime.controller;

import enums.OrderStatus;
import id.ac.ui.cs.advprog.gametime.service.strategy.CartStockManagementStrategy;
import id.ac.ui.cs.advprog.gametime.model.*;
import id.ac.ui.cs.advprog.gametime.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CartStockManagementStrategy cartStockManagementStrategy;

    @GetMapping("")
    public String order(Model model) {
        UUID userId = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()).getUserID();
        Order order = orderService.getLatestOrder(userId.toString());

        int totalPrice = 0;
        int totalQuantity = 0;
        for (Game game : order.getGameQuantity().keySet()) {
            totalPrice += game.getPrice() * order.getGameQuantity().get(game);
            totalQuantity += order.getGameQuantity().get(game);
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

        Cart cart = cartService.getCartByUser(user);
        for (GameInCart gameInCart : cart.getGames()) {
            cartStockManagementStrategy.checkStockAvailability(gameInCart);
        }

        Order order = orderService.getLatestOrder(user.getUserID().toString());
        if (order == null
                || !order.getOrderStatus().equals(OrderStatus.WAITING_PAYMENT.getValue())) {
            order = new Order();
            order.setOrderDate(LocalDateTime.now());
            order.setOrderStatus(OrderStatus.WAITING_PAYMENT.getValue());
        }

        orderService.save(order, cart);
        return "redirect:/order";
    }

    @PostMapping("/pay/{id}")
    public String pay(@PathVariable String id) {
        User user = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        Order order = orderService.getOrderById(id);

        for (GameInCart gameInCart : order.getCart().getGames()) {
            cartStockManagementStrategy.checkStockAvailability(gameInCart);
        }

        List<GameInCart> cartGames = order.getCart().getGames();
        int totalPrice = 0;
        for (GameInCart gameInCart : cartGames) {
            Game game = gameInCart.getGame();
            totalPrice += gameInCart.getQuantity() * game.getPrice();
        }

        // TODO: Deduct the user's balance (oka's module)

        for (GameInCart gameInCart : cartGames) {
            Game game = gameInCart.getGame();
            game.getSeller().setBalance(gameInCart.getQuantity() * game.getPrice() + game.getSeller().getBalance());
            gameService.decreaseStock(game, gameInCart.getQuantity());
        }

        Transaction transaction = new Transaction(UUID.randomUUID(), user, order, OrderStatus.SUCCESS.getValue());
        transactionService.create(transaction);

        orderService.setStatus(OrderStatus.SUCCESS.getValue(), order);
        cartService.clearCart(user);

        return "redirect:/game/buyer";
    }

    @PostMapping("/remove/{gameId}")
    public String remove(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        Cart cart = cartService.getCartByUser(customer);
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId, cart.getCartId().toString());
        cartService.removeGameFromCart(customer,gameInCart);
        return "redirect:/order";
    }
}
