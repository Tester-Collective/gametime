package id.ac.ui.cs.advprog.gametime.service.decorator;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceDecoratorTest {

    @Mock
    private CartService cartServiceImpl;

    @InjectMocks
    private CartServiceDecorator cartServiceDecorator;

    private User testUser;
    private Cart testCart;
    private Game testGame;
    private GameInCart testGameInCart;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setUsername("testUser");

        testCart = new Cart();
        testCart.setCartId(UUID.fromString("11111111-1111-1111-1111-111111111111")); // Example UUID
        testCart.setCustomer(testUser);

        testGame = new Game();
        testGame.setId(UUID.fromString("22222222-2222-2222-2222-222222222222")); // Example UUID
        testGame.setTitle("Test Game");
        testGame.setPrice(50);

        testGameInCart = new GameInCart();
        testGameInCart.setGame(testGame);
        testGameInCart.setQuantity(1);
        testGameInCart.setCart(testCart);
    }

    @Test
    public void getCartByUser_delegatesToDecoratedService() {
        when(cartServiceImpl.getCartByUser(any())).thenReturn(testCart);

        Cart result = cartServiceDecorator.getCartByUser(testUser);

        assertEquals(testCart, result);
        verify(cartServiceImpl, times(1)).getCartByUser(testUser);
    }

    @Test
    public void getGameInCartByGameId_delegatesToDecoratedService() {
        when(cartServiceImpl.getGameInCartByGameId(anyString(), anyString())).thenReturn(testGameInCart);

        GameInCart result = cartServiceDecorator.getGameInCartByGameId("22222222-2222-2222-2222-222222222222", "11111111-1111-1111-1111-111111111111");

        assertEquals(testGameInCart, result);
        verify(cartServiceImpl, times(1)).getGameInCartByGameId("22222222-2222-2222-2222-222222222222", "11111111-1111-1111-1111-111111111111");
    }

    @Test
    public void addGameToCart_delegatesToDecoratedService() {
        cartServiceDecorator.addGameToCart(testUser, testGameInCart);

        verify(cartServiceImpl, times(1)).addGameToCart(testUser, testGameInCart);
    }

    @Test
    public void removeGameFromCart_delegatesToDecoratedService() {
        cartServiceDecorator.removeGameFromCart(testUser, testGameInCart);

        verify(cartServiceImpl, times(1)).removeGameFromCart(testUser, testGameInCart);
    }

    @Test
    public void increaseGameQuantity_delegatesToDecoratedService() {
        cartServiceDecorator.increaseGameQuantity(testUser, testGameInCart);

        verify(cartServiceImpl, times(1)).increaseGameQuantity(testUser, testGameInCart);
    }

    @Test
    public void decreaseGameQuantity_quantityGreaterThanOne_delegatesToDecoratedService() {
        testGameInCart.setQuantity(2);
        cartServiceDecorator.decreaseGameQuantity(testUser, testGameInCart);

        verify(cartServiceImpl, times(1)).decreaseGameQuantity(testUser, testGameInCart);
    }

    @Test
    public void decreaseGameQuantity_quantityEqualsOne_removeGameFromCart() {
        testGameInCart.setQuantity(1);
        cartServiceDecorator.decreaseGameQuantity(testUser, testGameInCart);

        verify(cartServiceImpl, times(1)).removeGameFromCart(testUser, testGameInCart);
    }

    @Test
    public void clearCart_delegatesToDecoratedService() {
        cartServiceDecorator.clearCart(testUser);

        verify(cartServiceImpl, times(1)).clearCart(testUser);
    }

    @Test
    public void getTotalPrice_delegatesToDecoratedService() {
        when(cartServiceImpl.getTotalPrice(any())).thenReturn(100);

        Integer result = cartServiceDecorator.getTotalPrice(testUser);

        assertEquals(100, result);
        verify(cartServiceImpl, times(1)).getTotalPrice(testUser);
    }

    @Test
    public void getTotalQuantity_delegatesToDecoratedService() {
        when(cartServiceImpl.getTotalQuantity(any())).thenReturn(5);

        Integer result = cartServiceDecorator.getTotalQuantity(testUser);

        assertEquals(5, result);
        verify(cartServiceImpl, times(1)).getTotalQuantity(testUser);
    }

    @Test
    public void getAllCarts_delegatesToDecoratedService() {
        cartServiceDecorator.getAllCarts();

        verify(cartServiceImpl, times(1)).getAllCarts();
    }
}
