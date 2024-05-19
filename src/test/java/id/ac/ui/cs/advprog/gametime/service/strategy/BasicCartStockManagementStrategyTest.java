package id.ac.ui.cs.advprog.gametime.service.strategy;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BasicCartStockManagementStrategyTest {

    private BasicCartStockManagementStrategy strategy;
    private Game game;
    private GameInCart gameInCart;

    @BeforeEach
    void setUp() {
        strategy = new BasicCartStockManagementStrategy();
        game = new Game();
        gameInCart = new GameInCart();
        gameInCart.setGame(game);
    }

    @Test
    void testCheckStockAvailability_SufficientStock() {
        game.setStock(10);
        gameInCart.setQuantity(5);

        assertDoesNotThrow(() -> strategy.checkStockAvailability(gameInCart));
    }

    @Test
    void testCheckStockAvailability_InsufficientStock() {
        game.setStock(5);
        gameInCart.setQuantity(10);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> strategy.checkStockAvailability(gameInCart));
        assertEquals("Insufficient stock for " + game.getTitle(), exception.getMessage());
    }

    @Test
    void testCheckStockAvailability_ExactStock() {
        game.setStock(5);
        gameInCart.setQuantity(5);

        assertDoesNotThrow(() -> strategy.checkStockAvailability(gameInCart));
    }
}
