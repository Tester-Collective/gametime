package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;

public interface GameService {
    Game deleteGameById(String id);

    Game getGameById(String id);

    Game addGame(Game game);

    Game updateGame(String id, Game game);

    Iterable<Game> getAllGames();

}
