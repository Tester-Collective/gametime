package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public interface GameService {
    Game add(Game game);
    Iterator<Game> findAll();
    boolean delete(String id);
    Game update(Game game, String id);
    Game findById(String id);
}
