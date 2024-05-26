package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FilterService {

    List<Game> getGamesByKeyword(String keyword);

    List<Game> getTopThreeFreeGames();

    List<Game> getGamesByPlatformOrderByRating(String platform);

    List<Game> getGamesTop6OrderByRating();

    List<Game> getAllGamesWithConstraint();
}
