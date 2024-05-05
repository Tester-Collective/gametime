package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.User;

import java.util.List;

public interface GameService {
    void deleteGameById(String id);

    Game getGameById(String id);

    Game addGame(Game game);

    Game updateGame(String id, Game game);

    List<Game> getAllGames();

    public List<Game> findGamesBySeller(User seller);

}