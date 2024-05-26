package id.ac.ui.cs.advprog.gametime.model;

import enums.Categories;
import enums.Platform;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    @Test
    void testGetGameID() {
        this.game = new Game();
        this.game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        assertEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"), this.game.getId());
    }

    @Test
    void testGetTitle() {
        this.game = new Game();
        this.game.setTitle("game");
        assertEquals("game", this.game.getTitle());
    }

    @Test
    void testGetGameDescription() {
        this.game = new Game();
        this.game.setDescription("description");
        assertEquals("description", this.game.getDescription());
    }

    @Test
    void testGetGamePrice() {
        this.game = new Game();
        this.game.setPrice(100);
        assertEquals(100, this.game.getPrice());
    }

    @Test
    void testGetGamePicture() {
        this.game = new Game();
        this.game.setImageName("game.jpg");
        assertEquals("game.jpg", this.game.getImageName());
    }


    @Test
    void testGetCategory() {
        Category category = new Category();
        category.setName("RPG");
        this.game = new Game();
        this.game.setCategory(category);
        assertEquals(category, this.game.getCategory());
    }

    @Test
    void testGetStock() {
        this.game = new Game();
        this.game.setStock(100);
        assertEquals(100, this.game.getStock());
    }

    @Test
    void testGetPlatform() {
        this.game = new Game();
        this.game.setPlatform(Platform.PC.getValue());
        assertEquals(Platform.PC.getValue(), this.game.getPlatform());
    }

    @Test
    void testGetSeller() {
        User seller = new User();
        seller.setUsername("seller");

        this.game = new Game();
        this.game.setSeller(seller);
        assertEquals(seller, this.game.getSeller());
    }

    @Test
    void testGetGameDeleted() {
        this.game = new Game();
        this.game.setGameDeleted(true);
        assertTrue(this.game.isGameDeleted());
    }

    @Test
    void testGetAvgRating() {
        this.game = new Game();
        this.game.setAvgRating(4.5f);
        assertEquals(4.5, this.game.getAvgRating());
    }

    @Test
    void testSetPlatformPC() {
        this.game = new Game();
        this.game.setPlatform(Platform.PC.getValue());
        assertEquals(Platform.PC.getValue(), this.game.getPlatform());
        assertTrue(Platform.contains(this.game.getPlatform()));
    }

    @Test
    void testSetPlatformNegative() {
        this.game = new Game();
        this.game.setPlatform("PS4");
        assertFalse(Platform.contains(this.game.getPlatform()));
    }

    @Test
    void testSetCategory() {
        Category category = new Category();
        category.setName(Categories.ACTION.getValue());
        this.game = new Game();
        this.game.setCategory(category);
        assertEquals(category, this.game.getCategory());
        assertTrue(Categories.contains(this.game.getCategory().getName()));
    }

    @Test
    void testSetCategoryNegative() {
        Category category = new Category();
        category.setName("NON_EXISTENT_CATEGORY");
        this.game = new Game();
        this.game.setCategory(category);
        assertEquals(category, this.game.getCategory());
        assertFalse(Categories.contains(this.game.getCategory().getName()));
    }
}
