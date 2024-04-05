package id.ac.ui.cs.advprog.gametime.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import id.ac.ui.cs.advprog.gametime.model.Game;

import static org.junit.jupiter.api.Assertions.*;

public class GameRepositoryTest {
    GameRepositoryImpl gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository = new GameRepositoryImpl();
    }

    @Test
    void testAddGame() {
        Game game = new Game();
        gameRepository.add(game);
        assertTrue(gameRepository.findAll().hasNext());
    }

    @Test
    void testFindAll() {
        Game game = new Game();
        gameRepository.add(game);
        List<Game> games = new ArrayList<>();
        gameRepository.findAll().forEachRemaining(games::add);
        assertEquals(1, games.size());
    }

    @Test
    void testFindById() {
        Game game = new Game();
        game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        gameRepository.add(game);
        assertEquals(game, gameRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }

    @Test
    void testDeleteById() {
        Game game = new Game();
        game.setId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        gameRepository.add(game);
        assertTrue(gameRepository.deleteById("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }
}
