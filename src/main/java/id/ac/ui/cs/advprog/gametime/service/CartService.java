package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Cart;
public interface CartService {
    void deleteGameFromCart(String cartId,Game game);

    Cart decreaseGameQuantity(String cartId, Game game);

    Cart increaseGameQuantity(String cartId, Game game);

    Cart addGameToCart(String cartId, Game game);


}
