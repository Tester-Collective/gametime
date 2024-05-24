package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.CartRepository;

import java.util.List;

public interface CartService {

    Cart getCartByUser(User user);
    GameInCart getGameInCartByGameId(String gameId, String cartId);
    void addGameToCart(User user, GameInCart game);
    void removeGameFromCart(User user, GameInCart game);
    void increaseGameQuantity(User user, GameInCart game);
    void decreaseGameQuantity(User user, GameInCart game);
    void clearCart(User user);

    List<Cart> getAllCarts();

}
