package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.repository.FilterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FilterServiceTest {

    private List<Game> games;
    @Mock
    private FilterRepository filterRepository;

    @InjectMocks
    private FilterServiceImpl filterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        List<Game> games = new ArrayList<>();

        // Game 1
        Game game = new Game();
        game.setId(UUID.randomUUID());
        game.setTitle("CS:GO");

        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("FPS");

        game.setCategory(category);
        game.setPrice(10);
        games.add(game);

        // Game 2
        Game game2 = new Game();
        game2.setId(UUID.randomUUID());
        game2.setTitle("Dota 2");

        Category category2 = new Category();
        category2.setId(UUID.randomUUID());
        category2.setName("MOBA");

        game2.setCategory(category2);
        game2.setPrice(20);
        games.add(game2);

        // Game 3
        Game game3 = new Game();
        game3.setId(UUID.randomUUID());
        game3.setTitle("Valorant");

        Category category3 = new Category();
        category3.setId(UUID.randomUUID());
        category3.setName("FPS");

        game3.setCategory(category3);
        game3.setPrice(30);
        games.add(game3);
    }

    @Test
    public void testAllParam() {
        // Filter
        String keyword = "CS";
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("FPS");
        String platform = "PC";
        int minPrice = 0;
        int maxPrice = 100;

        // Games
        List<Game> expectedGames = new ArrayList<>();
        Game game4 = new Game();
        game4.setId(UUID.randomUUID());
        game4.setTitle("CS:GO");
        Category categoryGame = new Category();
        categoryGame.setId(UUID.randomUUID());
        categoryGame.setName("FPS");
        game4.setCategory(categoryGame);
        game4.setPrice(10);
        expectedGames.add(game4);

        when(filterRepository.findByTitle(keyword)).thenReturn(expectedGames);
        when(filterRepository.findByCategory(category)).thenReturn(expectedGames);
        when(filterRepository.findByPlatform(platform)).thenReturn(expectedGames);
        when(filterRepository.findByPrice(minPrice, maxPrice)).thenReturn(expectedGames);
        List<Game> filteredGames = filterService.filterGame(keyword, category, platform, minPrice, maxPrice);
        assertEquals(expectedGames.getFirst(), filteredGames.getFirst());
    }

    @Test
    public void testParamKeyword() {
        // Filter
        String keyword = "CS";
        Category category = null;
        String platform = null;
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;

        // Games
        List<Game> expectedGames = new ArrayList<>();
        Game game4 = new Game();
        game4.setId(UUID.randomUUID());
        game4.setTitle("CS:GO");
        Category categoryGame = new Category();
        categoryGame.setId(UUID.randomUUID());
        categoryGame.setName("FPS");
        game4.setCategory(categoryGame);
        game4.setPrice(10);
        expectedGames.add(game4);

        when(filterRepository.findByTitle(keyword)).thenReturn(expectedGames);
        List<Game> filteredGames = filterService.filterGame(keyword, category, platform, minPrice, maxPrice);
        assertEquals(expectedGames.getFirst(), filteredGames.getFirst());
    }

    @Test
    public void testParamCategory() {
        // Filter
        String keyword = null;
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("FPS");
        String platform = null;
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;

        // Games
        List<Game> expectedGames = new ArrayList<>();
        Game game4 = new Game();
        game4.setId(UUID.randomUUID());
        game4.setTitle("CS:GO");
        Category categoryGame = new Category();
        categoryGame.setId(UUID.randomUUID());
        categoryGame.setName("FPS");
        game4.setCategory(categoryGame);
        game4.setPrice(10);
        expectedGames.add(game4);

        when(filterRepository.findByCategory(category)).thenReturn(expectedGames);
        List<Game> filteredGames = filterService.filterGame(keyword, category, platform, minPrice, maxPrice);
        assertEquals(expectedGames.getFirst(), filteredGames.getFirst());
    }

    @Test
    public void testParamPlatform() {
        // Filter
        String keyword = null;
        Category category = null;
        String platform = "PC";
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;

        // Games
        List<Game> expectedGames = new ArrayList<>();
        Game game4 = new Game();
        game4.setId(UUID.randomUUID());
        game4.setTitle("CS:GO");
        Category categoryGame = new Category();
        categoryGame.setId(UUID.randomUUID());
        categoryGame.setName("FPS");
        game4.setCategory(categoryGame);
        game4.setPrice(10);
        expectedGames.add(game4);

        when(filterRepository.findByPlatform(platform)).thenReturn(expectedGames);
        List<Game> filteredGames = filterService.filterGame(keyword, category, platform, minPrice, maxPrice);
        assertEquals(expectedGames.getFirst(), filteredGames.getFirst());
    }

    @Test
    public void testParamPrice() {
        // Filter
        String keyword = null;
        Category category = null;
        String platform = null;
        int minPrice = 5;
        int maxPrice = 15;

        // Games
        List<Game> expectedGames = new ArrayList<>();
        Game game4 = new Game();
        game4.setId(UUID.randomUUID());
        game4.setTitle("CS:GO");
        Category categoryGame = new Category();
        categoryGame.setId(UUID.randomUUID());
        categoryGame.setName("FPS");
        game4.setCategory(categoryGame);
        game4.setPrice(10);
        expectedGames.add(game4);

        when(filterRepository.findByPrice(minPrice, maxPrice)).thenReturn(expectedGames);
        List<Game> filteredGames = filterService.filterGame(keyword, category, platform, minPrice, maxPrice);
        assertEquals(expectedGames.getFirst(), filteredGames.getFirst());
    }
}
