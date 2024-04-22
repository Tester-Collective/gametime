package id.ac.ui.cs.advprog.gametime.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    Category category;

    @BeforeEach
    void setUp() {
        this.category = new Category();
        this.category.setName("Action");
        this.category.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }

    @Test
    void testGetName() {
        assertEquals("Action", this.category.getName());
    }

    @Test
    void testGetName_Negative() {
        assertNotEquals("Adventure", this.category.getName());
    }

    @Test
    void testGetId() {
        assertEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"), this.category.getId());
    }

    @Test
    void testGetId_Negative() {
        assertNotEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd7"), this.category.getId());
    }
}
