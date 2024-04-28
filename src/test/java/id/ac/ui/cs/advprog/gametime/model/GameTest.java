package id.ac.ui.cs.advprog.gametime.model;

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
        this.game.setImageLink("game.jpg");
        assertEquals("game.jpg", this.game.getImageLink());
    }

    @Test
    void testAddCategory() {
        Category category = new Category();
        category.setName("ACTION");

        this.game = new Game();
        this.game.addCategory(category);
        assertTrue(this.game.getCategories().contains(category));
    }

    @Test
    void testRemoveCategory() {
        Category category = new Category();
        category.setName("ACTION");

        this.game = new Game();
        this.game.addCategory(category);
        this.game.removeCategory(category);
        assertTrue(this.game.getCategories().isEmpty());
    }

    @Test
    void testGetCategory() {
        Category category = new Category();
        category.setName("RPG");
        this.game = new Game();
        this.game.setCategories(new ArrayList<>(Set.of(category)));
        assertTrue(this.game.getCategories().contains(category));
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
}
