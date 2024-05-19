package id.ac.ui.cs.advprog.gametime.service.strategy;

import id.ac.ui.cs.advprog.gametime.exception.QuantityValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NonNegativeQuantityStrategyTest {

    private NonNegativeQuantityStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new NonNegativeQuantityStrategy();
    }

    @Test
    void testValidateQuantity_ValidQuantity() {
        assertDoesNotThrow(() -> strategy.validateQuantity(1));
        assertDoesNotThrow(() -> strategy.validateQuantity(10));
    }

    @Test
    void testValidateQuantity_InvalidQuantity() {
        QuantityValidationException exception = assertThrows(QuantityValidationException.class, () -> strategy.validateQuantity(0));
        assertEquals("Quantity cannot be below 0", exception.getMessage());

        exception = assertThrows(QuantityValidationException.class, () -> strategy.validateQuantity(-5));
        assertEquals("Quantity cannot be below 0", exception.getMessage());
    }
}
