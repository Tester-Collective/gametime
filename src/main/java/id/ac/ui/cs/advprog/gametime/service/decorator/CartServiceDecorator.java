package id.ac.ui.cs.advprog.gametime.service.decorator;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Qualifier("decoratedCartService")
public class CartServiceDecorator implements CartService {
    private final CartService decoratedCartService;

    @Autowired
    public CartServiceDecorator(@Qualifier("cartServiceImpl") CartService decoratedCartService) {
        this.decoratedCartService = decoratedCartService;
    }

    @Override
    public Cart getCartByUser(User user) {
        return decoratedCartService.getCartByUser(user);
    }

    @Override
    public GameInCart getGameInCartByGameId(String gameId, String cartId) {
        return decoratedCartService.getGameInCartByGameId(gameId, cartId);
    }

    @Override
    public void addGameToCart(User user, GameInCart game) {
        decoratedCartService.addGameToCart(user, game);
    }

    @Override
    public void removeGameFromCart(User user, GameInCart game) {
        decoratedCartService.removeGameFromCart(user, game);
    }

    @Override
    public void increaseGameQuantity(User user, GameInCart game) {
        decoratedCartService.increaseGameQuantity(user, game);
    }

    @Override
    public void decreaseGameQuantity(User user, GameInCart game) {
        if (game.getQuantity() <= 1) {
            decoratedCartService.removeGameFromCart(user, game);
        } else {
            decoratedCartService.decreaseGameQuantity(user, game);
        }
    }

    @Override
    public void clearCart(User user) {
        decoratedCartService.clearCart(user);
    }

    @Override
    public Integer getTotalPrice(User user) {
        return decoratedCartService.getTotalPrice(user);
    }

    @Override
    public Integer getTotalQuantity(User user) {
        return decoratedCartService.getTotalQuantity(user);
    }

    @Override
    public List<Cart> getAllCarts() {
        return decoratedCartService.getAllCarts();
    }
}
