package id.ac.ui.cs.advprog.gametime.service.strategy;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.GameInCart;
import org.springframework.stereotype.Component;

@Component
public class BasicCartStockManagementStrategy implements CartStockManagementStrategy {

    @Override
    public void checkStockAvailability(GameInCart game) {
        Game actualGame = game.getGame();
        int requestedQuantity = game.getQuantity();
        if (actualGame.getStock() < requestedQuantity) {
            throw new RuntimeException("Insufficient stock for " + actualGame.getTitle());
        }
    }
}
