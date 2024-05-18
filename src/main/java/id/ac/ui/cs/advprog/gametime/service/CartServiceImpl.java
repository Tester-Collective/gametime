package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.service.strategy.CartStockManagementStrategy;
import id.ac.ui.cs.advprog.gametime.service.strategy.QuantityManagementStrategy;
import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.CartRepository;
import id.ac.ui.cs.advprog.gametime.repository.GameInCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GameInCartRepository gameInCartRepository;

    @Autowired
    private CartStockManagementStrategy cartStockManagementStrategy;

    @Override
    public Cart getCartByUser(User user) {
        return cartRepository.findCartByCustomer(user);
    }

    @Autowired
    private QuantityManagementStrategy quantityManagementStrategy;

    @Override
    public GameInCart getGameInCartByGameId(String gameId, String cartId) {
        return gameInCartRepository.findGameInCartByGame_IdAndCart_CartId(UUID.fromString(gameId), UUID.fromString(cartId));
    }

    @Override
    public void addGameToCart(User user, GameInCart game) {
        Cart cart = cartRepository.findCartByCustomer(user);
        cartStockManagementStrategy.checkStockAvailability(game);
        cart.addGame(game);
        gameInCartRepository.save(game);
    }

    @Override
    public void removeGameFromCart(User user, GameInCart game) {
        Cart cart = cartRepository.findCartByCustomer(user);
        cart.removeGame(game);
        gameInCartRepository.delete(game);
    }

    @Override
    public void increaseGameQuantity(User user, GameInCart game) {
        Cart cart = cartRepository.findCartByCustomer(user);
        cartStockManagementStrategy.checkStockAvailability(game);
        cart.increaseGameQuantity(game);
        gameInCartRepository.save(game);
    }

    @Override
    public void decreaseGameQuantity(User user, GameInCart game) {
        Cart cart = cartRepository.findCartByCustomer(user);
        quantityManagementStrategy.validateQuantity(game.getQuantity());
        cart.decreaseGameQuantity(game);
        gameInCartRepository.save(game);
    }

    @Transactional
    @Override
    public void clearCart(User user) {
        Cart cart = cartRepository.findCartByCustomer(user);
        cart.clearCart();
        gameInCartRepository.deleteAllByCart_CartId(cart.getCartId());
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}
