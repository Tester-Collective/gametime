package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;

    public void deleteGameById(String id) {
        gameRepository.deleteById(UUID.fromString(id));
    }

    public Game getGameById(String id) {
        return gameRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    public Game updateGame(String id, Game game) {
        game.setId(UUID.fromString(id));
        return gameRepository.save(game);
    }

    public List<Game> getAllGames() {
        return gameRepository.findByOrderByTitle();
    }

    public List<Game> findGamesBySeller(User seller) {
        return gameRepository.findGamesBySeller(seller);
    }

    public Game decreaseStock(Game game, int sub) {
        game.setStock(game.getStock() - sub);
        return gameRepository.save(game);
    }
}
