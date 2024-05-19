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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class FilterServiceTest {

    @Mock
    private FilterRepository filterRepository;

    @InjectMocks
    private FilterServiceImpl filterService;

    private List<Game> gameList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gameList = new ArrayList<>();

        Category FPS = new Category();
        FPS.setId(UUID.randomUUID());
        FPS.setName("FPS");

        Category STRATEGY = new Category();
        STRATEGY.setId(UUID.randomUUID());
        STRATEGY.setName("Strategy");

        Game game1 = new Game();
        game1.setId(UUID.randomUUID());
        game1.setTitle("Test Game 1");
        game1.setCategory(FPS);
        game1.setPlatform("PC");
        game1.setPrice(50);

        Game game2 = new Game();
        game2.setId(UUID.randomUUID());
        game2.setTitle("Another Test Game");
        game2.setCategory(STRATEGY);
        game2.setPlatform("Console");
        game2.setPrice(0);

        gameList.add(game1);
        gameList.add(game2);
    }

    // Test commit because Rafif used different github account (ACCIDENTALLY)
    @Test
    public void testFilterGameByCategory() {
        Category FPS = new Category();
        FPS.setId(UUID.randomUUID());
        FPS.setName("FPS");

        when(filterRepository.findByCategoryOrderByTitle(FPS)).thenReturn(List.of(gameList.getFirst()));

        List<Game> result = filterService.filterGame(null, FPS, null, 0, Integer.MAX_VALUE);
        assertThat(result).hasSize(1).contains(gameList.getFirst());
    }

    @Test
    public void testFilterGameByPlatform() {
        when(filterRepository.findByPlatformOrderByTitle("PC")).thenReturn(List.of(gameList.getFirst()));

        List<Game> result = filterService.filterGame(null, null, "PC", 0, Integer.MAX_VALUE);
        assertThat(result).hasSize(1).contains(gameList.getFirst());
    }

    @Test
    public void testFilterGameByPriceRange() {
        when(filterRepository.findByPriceBetweenOrderByTitle(40, 55)).thenReturn(List.of(gameList.getFirst()));

        List<Game> result = filterService.filterGame(null, null, null, 40, 55);
        assertThat(result).hasSize(1).contains(gameList.getFirst());
    }

    @Test
    public void testFilterGameByPriceFree() {
        when(filterRepository.findByPriceEqualsOrderByTitle(0)).thenReturn(List.of(gameList.get(1)));

        List<Game> result = filterService.filterGame(null, null, null, 0, 0);
        assertThat(result).hasSize(1).contains(gameList.get(1));
    }

    @Test
    public void testFilterGameByTitleKeyword() {
        when(filterRepository.findByTitleIgnoreCaseOrderByTitle("test")).thenReturn(gameList);

        List<Game> result = filterService.filterGame("test", null, null, 0, Integer.MAX_VALUE);
        assertThat(result).hasSize(2).containsExactlyInAnyOrderElementsOf(gameList);
    }

    @Test
    public void testFilterGameWithNoCriteria() {
        when(filterRepository.findByOrderByTitle()).thenReturn(gameList);

        List<Game> result = filterService.filterGame(null, null, null, 0, Integer.MAX_VALUE);
        assertThat(result).hasSize(2).containsExactlyInAnyOrderElementsOf(gameList);
    }
}
