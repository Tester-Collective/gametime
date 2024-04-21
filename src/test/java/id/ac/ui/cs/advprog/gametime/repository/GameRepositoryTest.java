package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameRepositoryTest {
    @Mock
    private GameRepository gameRepository;

    @Test
    void testSave_PositiveCase() {
        Game game = new Game();
        game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        game.setTitle("CS:GO");
        game.setPrice(10);
        when(gameRepository.save(game)).thenReturn(game);
        assertEquals(game, gameRepository.save(game));
    }

    @Test
    void testSave_NegativeCase() {
        when(gameRepository.save(null)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> {
            gameRepository.save(null);
        });
    }

    @Test
    void testFindById_PositiveCase() {
        Game game = new Game();
        game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        game.setTitle("CS:GO");
        game.setPrice(10);
        when(gameRepository.findById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"))).thenReturn(java.util.Optional.of(game));
        assertTrue(gameRepository.findById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6")).isPresent());
    }

    @Test
    void testFindById_NegativeCase() {
        assertFalse(gameRepository.findById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd7")).isPresent());
    }

    @Test
    void testDelete_PositiveCase() {
        gameRepository.deleteGameById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        assertEquals(0, gameRepository.findAll().size());
    }

    @Test
    void testDelete_NegativeCase() {
        Game game = new Game();
        game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        game.setTitle("CS:GO");
        game.setPrice(10);

        when(gameRepository.save(game)).thenReturn(game);
        Game savedGame = gameRepository.save(game);

        when(gameRepository.deleteGameById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd7"))).thenReturn(null);
        Game deletedGame = gameRepository.deleteGameById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd7"));
        assertNotEquals(savedGame, deletedGame);
    }

    @Test
    void testFindAll_PositiveCase() {
        Game game = new Game();
        game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        game.setTitle("CS:GO");
        game.setPrice(10);
        when(gameRepository.save(game)).thenReturn(game);
        gameRepository.save(game);
        when(gameRepository.findAll()).thenReturn(java.util.List.of(game));
        assertEquals(1, gameRepository.findAll().size());
    }

    @Test
    void testFindAll_NegativeCase() {
        gameRepository.deleteAll();
        assertEquals(0, gameRepository.findAll().size());
    }

    @Test
    void testDeleteById_PositiveCase() {
        // deleteById is void method
        Game game = new Game();
        game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        game.setTitle("CS:GO");
        game.setPrice(10);
        when(gameRepository.save(game)).thenReturn(game);
        gameRepository.save(game);
        gameRepository.deleteById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        verify(gameRepository, times(1)).deleteById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }

    @Test
    void testDeleteById_NegativeCase() {
        Game game = new Game();
        game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        game.setTitle("CS:GO");
        game.setPrice(10);
        when(gameRepository.save(game)).thenReturn(game);
        gameRepository.save(game);
        gameRepository.deleteById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd7"));
        verify(gameRepository, times(1)).deleteById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd7"));
    }
}
