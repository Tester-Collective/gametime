package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Game;

import java.util.Iterator;

public interface GameRepository {
     public Game add(Game game);
     public Iterator<Game> findAll();
     public Game findById(String id);
     public boolean deleteById(String id);
     public Game update(Game game, String id);
}
