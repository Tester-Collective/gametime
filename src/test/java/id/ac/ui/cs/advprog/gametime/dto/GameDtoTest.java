package id.ac.ui.cs.advprog.gametime.dto;

import id.ac.ui.cs.advprog.gametime.model.Category;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameDtoTest {
    GameDto gameDto;

    @Test
    void testGetGameID() {
        this.gameDto = new GameDto();
        this.gameDto.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        assertEquals(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"), this.gameDto.getId());
    }

    @Test
    void testGetTitle() {
        this.gameDto = new GameDto();
        this.gameDto.setTitle("game");
        assertEquals("game", this.gameDto.getTitle());
    }

    @Test
    void testGetGameDescription() {
        this.gameDto = new GameDto();
        this.gameDto.setDescription("description");
        assertEquals("description", this.gameDto.getDescription());
    }

    @Test
    void testGetGamePrice() {
        this.gameDto = new GameDto();
        this.gameDto.setPrice(100);
        assertEquals(100, this.gameDto.getPrice());
    }

    @Test
    void testGetGameStock() {
        this.gameDto = new GameDto();
        this.gameDto.setStock(10);
        assertEquals(10, this.gameDto.getStock());
    }

    @Test
    void testGetGameCategory() {
        this.gameDto = new GameDto();
        Category category = new Category();
        category.setName("RPG");
        this.gameDto.setCategory(category);
        assertEquals(category, this.gameDto.getCategory());
    }

    @Test
    void testGetGamePlatform() {
        this.gameDto = new GameDto();
        this.gameDto.setPlatform("PC");
        assertEquals("PC", this.gameDto.getPlatform());
    }
}
