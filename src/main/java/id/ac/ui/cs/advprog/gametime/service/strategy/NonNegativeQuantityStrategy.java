package id.ac.ui.cs.advprog.gametime.service.strategy;

import id.ac.ui.cs.advprog.gametime.exception.QuantityValidationException;
import org.springframework.stereotype.Component;

@Component
public class NonNegativeQuantityStrategy implements QuantityManagementStrategy {
    @Override
    public void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new QuantityValidationException("Quantity cannot be below 0");
        }
    }
}

