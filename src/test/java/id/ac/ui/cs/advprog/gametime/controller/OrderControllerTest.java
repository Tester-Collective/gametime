import id.ac.ui.cs.advprog.gametime.controller.OrderController;
import id.ac.ui.cs.advprog.gametime.model.*;
import id.ac.ui.cs.advprog.gametime.service.*;
import id.ac.ui.cs.advprog.gametime.service.strategy.CartStockManagementStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    Model model;

    @Mock
    UserService userService;

    @Mock
    CartService cartService;

    @Mock
    OrderService orderService;

    @Mock
    TransactionService transactionService;

    @Mock
    CartStockManagementStrategy cartStockManagementStrategy;

    @InjectMocks
    OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDisplayOrderPage() {
        User user = new User();

        when(userService.getLoggedInUser()).thenReturn(user);

        orderController.order(model);

        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    void shouldHandlePaymentWhenBalanceIsSufficient() {
        User user = new User();
        user.setBalance(1000);
        Cart cart = new Cart();
        GameInCart gameInCart = new GameInCart();
        gameInCart.setGame(new Game());
        gameInCart.setQuantity(1);
        List<GameInCart> games = new ArrayList<>();
        games.add(gameInCart);
        cart.setGames(games);

        when(userService.getLoggedInUser()).thenReturn(user);
        when(cartService.getCartByUser(user)).thenReturn(cart);

        orderController.removeGameFromCart();

        verify(cartService, times(1)).clearCart(user);
    }
}