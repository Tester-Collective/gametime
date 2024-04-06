package id.ac.ui.cs.advprog.gametime.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FilterTest {

    @Test
    public void testFilterCreation() {
        // Create a Filter object with specific criteria
        Filter filter = new Filter("Counter Strike", "Action", "PC", 0, 50);

        // Check if the attributes are set correctly
        assertEquals("Counter Strike", filter.getSearchQuery());
        assertEquals("Action", filter.getCategory());
        assertEquals("PC", filter.getPlatform());
        assertEquals(0, filter.getMinPrice());
        assertEquals(50, filter.getMaxPrice());
    }
}
