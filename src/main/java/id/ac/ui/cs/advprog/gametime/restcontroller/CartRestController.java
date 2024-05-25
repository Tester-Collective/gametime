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

    @PostMapping("/increase/{gameId}")
    public ResponseEntity<?> increaseGameQuantity(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Cart cart = cartService.getCartByUser(customer);
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId, cart.getCartId().toString());

        if (gameInCart != null) {
            cartService.increaseGameQuantity(customer, gameInCart);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/decrease/{gameId}")
    public ResponseEntity<?> decreaseGameQuantity(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Cart cart = cartService.getCartByUser(customer);
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId, cart.getCartId().toString());

        cartService.decreaseGameQuantity(customer, gameInCart);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove/{gameId}")
    public ResponseEntity<?> removeGameFromCart(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Cart cart = cartService.getCartByUser(customer);
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId, cart.getCartId().toString());

        if (gameInCart != null) {
            cartService.removeGameFromCart(customer, gameInCart);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/quantity/{gameId}")
    public Integer getGameQuantity(@PathVariable String gameId) {
        User customer = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Cart cart = cartService.getCartByUser(customer);
        GameInCart gameInCart = cartService.getGameInCartByGameId(gameId, cart.getCartId().toString());

        if (gameInCart != null) {
            return gameInCart.getQuantity();
        }

        return 0;
    }

    @GetMapping("/total-quantity")
    public Integer getTotalQuantity() {
        User customer = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Cart cart = cartService.getCartByUser(customer);
        return cartService.getTotalQuantity(customer);
    }

    @GetMapping("/total-price")
    public Integer getTotalPrice() {
        User customer = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Cart cart = cartService.getCartByUser(customer);

        return cartService.getTotalPrice(customer);
    }
    @PostMapping("/clear")
    public ResponseEntity<?> clearCart() {
        User customer = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        cartService.clearCart(customer);

        return ResponseEntity.ok().build();
    }
}
