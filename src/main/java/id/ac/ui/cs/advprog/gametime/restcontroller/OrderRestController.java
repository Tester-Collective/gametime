package id.ac.ui.cs.advprog.gametime.restcontroller;

import enums.OrderStatus;
import id.ac.ui.cs.advprog.gametime.dto.OrderDto;
import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CartService;
import id.ac.ui.cs.advprog.gametime.service.OrderService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import id.ac.ui.cs.advprog.gametime.service.strategy.CartStockManagementStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartStockManagementStrategy cartStockManagementStrategy;

    @GetMapping("/create")
    public OrderDto createOrder() {
        User user = userService.getLoggedInUser();

        Cart cart = cartService.getCartByUser(user);
        for (GameInCart gameInCart : cart.getGames()) {
            cartStockManagementStrategy.checkStockAvailability(gameInCart);
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setCartId(cart.getCartId().toString());
        orderDto.setOrderStatus(OrderStatus.WAITING_PAYMENT.getValue());
        orderDto.setOrderDate(LocalDateTime.now());
        for (GameInCart gameInCart : cart.getGames()) {
            orderDto.getGameQuantity().put(gameInCart.getGame().getId().toString(), gameInCart.getQuantity());
        }
        return orderDto;
    }

}
