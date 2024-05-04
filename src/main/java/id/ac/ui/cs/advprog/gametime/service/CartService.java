package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.repository.CartRepository;

import java.util.List;

public interface CartService {

    Cart getCartByUser(String username);
    GameInCart getGameInCartByGameId(String gameId);
    void addGameToCart(String username, GameInCart game);
    void removeGameFromCart(String username, GameInCart game);
    void increaseGameQuantity(String username, GameInCart game);
    void decreaseGameQuantity(String username, GameInCart game);
    void clearCart(String username);

    List<Cart> getAllCarts();

}
