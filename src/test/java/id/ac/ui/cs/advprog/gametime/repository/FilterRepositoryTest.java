package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilterRepositoryTest {

    private List<Game> games;
    private Category category;

    @Mock
    private FilterRepository filterRepository;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("FPS");

        List<Game> games = new ArrayList<>();
        Game game = new Game();
        game.setId(UUID.randomUUID());
        game.setTitle("CS:GO");
        game.setCategory(category);
        game.setPrice(10);
        games.add(game);
    }

    @Test
    void testFindByTitle_PositiveCase() {
        String keyword = "CS";
        when(filterRepository.findByTitle(keyword)).thenReturn(games);
        assertEquals(games, filterRepository.findByTitle(keyword));
    }

    @Test
    void testFindByTitle_NegativeCase() {
        String keyword = "Ester";
        when(filterRepository.findByTitle(keyword)).thenReturn(new ArrayList<>());
        assertTrue(filterRepository.findByTitle(keyword).isEmpty());
    }

    @Test
    void testFindByCategory_PositiveCase() {
        when(filterRepository.findByCategory(category)).thenReturn(games);
        assertEquals(games, filterRepository.findByCategory(category));
    }

    @Test
    void testFindByCategory_NegativeCase() {
        Category category2 = new Category();
        category2.setId(UUID.randomUUID());
        category2.setName("RPG");
        when(filterRepository.findByCategory(category2)).thenReturn(new ArrayList<>());
        assertTrue(filterRepository.findByCategory(category2).isEmpty());
    }

    @Test
    void testFindByPrice_PositiveCase() {
        int minPrice = 5;
        int maxPrice = 15;
        when(filterRepository.findByPrice(minPrice, maxPrice)).thenReturn(games);
        assertEquals(games, filterRepository.findByPrice(minPrice, maxPrice));
    }

    @Test
    void testFindByPrice_NegativeCase() {
        int minPrice = 15;
        int maxPrice = 20;
        when(filterRepository.findByPrice(minPrice, maxPrice)).thenReturn(new ArrayList<>());
        assertTrue(filterRepository.findByPrice(minPrice, maxPrice).isEmpty());
    }

    @Test
    void testFindByPlatform_PositiveCase() {
        String platform = "PC";
        when(filterRepository.findByPlatform(platform)).thenReturn(games);
        assertEquals(games, filterRepository.findByPlatform(platform));
    }

    @Test
    void testFindByPlatform_NegativeCase() {
        String platform = "PS4";
        when(filterRepository.findByPlatform(platform)).thenReturn(new ArrayList<>());
        assertTrue(filterRepository.findByPlatform(platform).isEmpty());
    }
}
