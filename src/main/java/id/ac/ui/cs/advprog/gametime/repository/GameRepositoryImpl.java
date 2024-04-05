package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Game;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class GameRepositoryImpl implements GameRepository{
    private List<Game> gameData = new ArrayList<>();

    public Game add(Game game) {
        gameData.add(game);
        return game;
    }

    public Iterator<Game> findAll() {
        return gameData.iterator();
    }

    public Game findById(String id) {
        for (Game game : gameData) {
            if (game.getId().toString().equals(id)) {
                return game;
            }
        }
        return null;
    }

    public boolean deleteById(String id) {
        for (Game game : gameData) {
            if (game.getId().toString().equals(id)) {
                gameData.remove(game);
                return true;
            }
        }
        return false;
    }

    public Game update(Game game) {
        for (Game curGame : gameData) {
            if (curGame.getId().equals(game.getId())) {
                curGame.setTitle(game.getTitle());
                curGame.setDescription(game.getDescription());
                curGame.setPrice(game.getPrice());
                curGame.setImageLink(game.getImageLink());
                return curGame;
            }
        }
        return null;
    }
}
