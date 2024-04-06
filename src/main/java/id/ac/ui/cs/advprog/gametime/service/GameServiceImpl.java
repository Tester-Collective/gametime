package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game add(Game game) {
        return gameRepository.add(game);
    }

    @Override
    public Iterator<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public boolean delete(String id) {
        return gameRepository.deleteById(id);
    }

    @Override
    public Game update(Game game, String id) {
        return gameRepository.update(game, id);
    }

    @Override
    public Game findById(String id) {
        return gameRepository.findById(id);
    }
}
