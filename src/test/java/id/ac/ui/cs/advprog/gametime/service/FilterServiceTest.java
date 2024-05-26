package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.repository.FilterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilterServiceTest {

    @Mock
    private FilterRepository filterRepository;

    @InjectMocks
    private FilterServiceImpl filterService;

    private List<Game> games;

    @BeforeEach
    void setUp() {
        Game game1 = new Game();
        Game game2 = new Game();
        Game game3 = new Game();
        games = Arrays.asList(game1, game2, game3);
    }

    @Test
    void testGetGamesByKeyword() {
        String keyword = "test";
        when(filterRepository.findGamesByGameDeletedAndTitleContainingIgnoreCaseAndStockGreaterThanOrderByTitle(false, keyword, 0)).thenReturn(games);

        List<Game> result = filterService.getGamesByKeyword(keyword);

        assertEquals(games, result);
        verify(filterRepository, times(1)).findGamesByGameDeletedAndTitleContainingIgnoreCaseAndStockGreaterThanOrderByTitle(false, keyword, 0);
    }

    @Test
    void testGetTopThreeFreeGames() {
        when(filterRepository.findTop3ByPriceEqualsOrderByAvgRatingDesc(0)).thenReturn(games);

        List<Game> result = filterService.getTopThreeFreeGames();

        assertEquals(games, result);
        verify(filterRepository, times(1)).findTop3ByPriceEqualsOrderByAvgRatingDesc(0);
    }

    @Test
    void testGetGamesByPlatformOrderByRating() {
        String platform = "PC";
        when(filterRepository.findTop6ByGameDeletedAndPlatformAndStockGreaterThanOrderByAvgRatingDesc(false, platform, 0)).thenReturn(games);

        List<Game> result = filterService.getGamesByPlatformOrderByRating(platform);

        assertEquals(games, result);
        verify(filterRepository, times(1)).findTop6ByGameDeletedAndPlatformAndStockGreaterThanOrderByAvgRatingDesc(false, platform, 0);
    }

    @Test
    void testGetGamesTop6OrderByRating() {
        when(filterRepository.findTop6ByGameDeletedAndStockGreaterThanOrderByAvgRatingDesc(false, 0)).thenReturn(games);

        List<Game> result = filterService.getGamesTop6OrderByRating();

        assertEquals(games, result);
        verify(filterRepository, times(1)).findTop6ByGameDeletedAndStockGreaterThanOrderByAvgRatingDesc(false, 0);
    }

    @Test
    void testGetAllGamesWithConstraint() {
        when(filterRepository.findByGameDeletedAndStockGreaterThanOrderByTitle(false, 0)).thenReturn(games);

        List<Game> result = filterService.getAllGamesWithConstraint();

        assertEquals(games, result);
        verify(filterRepository, times(1)).findByGameDeletedAndStockGreaterThanOrderByTitle(false, 0);
    }
}