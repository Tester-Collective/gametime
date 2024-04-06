package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.repository.GameRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private GameRepositoryImpl gameRepository;

    @InjectMocks
    GameServiceImpl gameService;

    Game game;

    @BeforeEach
    void setUp() {
        // create a new game and set up the attributes
        game = new Game();
        game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        game.setTitle("Game 1");
        game.setCategory(new HashSet<>(Set.of("Action", "Adventure")));
        game.setDescription("Description of Game 1");
        game.setPrice(100);
        game.setStock(10);
        game.setImageLink("https://example.com/game1.jpg");
        game.setReviewIdSet(new HashSet<>(Set.of(UUID.fromString("2c8aee1f-df0d-4e36-8814-f5883a8f7db1"))));
    }

    @Test
    void testAddGame() {
        when(gameRepository.add(game)).thenReturn(game);
        Game addedGame = gameService.add(game);
        assertEquals(game, addedGame);
        verify(gameRepository, times(1)).add(game);
    }

    @Test
    void testFindAll() {
        when(gameRepository.findAll()).thenReturn(Set.of(game).iterator());
        assertTrue(gameService.findAll().hasNext());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    void testDelete() {
        when(gameRepository.deleteById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(true);
        assertTrue(gameService.delete("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        verify(gameRepository, times(1)).deleteById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testUpdate() {
        // create an updated game
        Game updatedGame = new Game();
        updatedGame.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        updatedGame.setTitle("Updated Game");
        updatedGame.setDescription("Description of Updated Game");
        updatedGame.setPrice(200);
        updatedGame.setImageLink("https://example.com/updatedgame.jpg");

        when(gameRepository.update(updatedGame, "eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(updatedGame);
        Game savedGame = gameService.update(updatedGame, "eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(savedGame, updatedGame);
        verify(gameRepository, times(1)).update(updatedGame, "eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testFindById() {
        when(gameRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(game);
        Game foundGame = gameService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(game, foundGame);
        verify(gameRepository, times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }
}
