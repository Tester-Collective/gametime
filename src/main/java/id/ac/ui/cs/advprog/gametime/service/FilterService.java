package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;
import java.util.List;

public interface FilterService {

    List<Game> filterGame(String keyword, Category category, String platform, int minPrice, int maxPrice);

}
