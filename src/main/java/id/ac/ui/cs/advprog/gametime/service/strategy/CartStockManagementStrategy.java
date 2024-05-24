package id.ac.ui.cs.advprog.gametime.service.strategy;

import id.ac.ui.cs.advprog.gametime.model.GameInCart;

public interface CartStockManagementStrategy {
    void checkStockAvailability(GameInCart game);
}
