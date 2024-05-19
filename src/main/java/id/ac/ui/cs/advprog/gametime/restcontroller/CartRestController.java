package id.ac.ui.cs.advprog.gametime.restcontroller;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CartService;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private GameService gameService;

    @PostMapping("/add/{gameId}")
    public ResponseEntity<?> addGameToCart(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
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

        return ResponseEntity.ok().build();
    }
}
