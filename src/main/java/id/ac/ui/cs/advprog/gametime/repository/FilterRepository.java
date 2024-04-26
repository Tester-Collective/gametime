package id.ac.ui.cs.advprog.gametime.repository;

import id.ac.ui.cs.advprog.gametime.model.Filter;
import id.ac.ui.cs.advprog.gametime.model.Game;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterRepository {
    private List<Game> games = new ArrayList<>();

    public List<Game> searchGames(Filter filter) {
        List<Game> filteredGames = new ArrayList<>();
        for (Game game : games) {
            // Apply filtering logic based on the provided filter
            if (matchesFilter(game, filter)) {
                filteredGames.add(game);
            }
        }
        return filteredGames;
    }

    private boolean matchesFilter(Game game, Filter filter) {
        // Check if the game matches the filter criteria
        return (filter.getSearchQuery() == null || game.getTitle().contains(filter.getSearchQuery()))
                && (filter.getCategory() == null || game.getCategories().stream().anyMatch(category -> category.getName().equalsIgnoreCase(filter.getCategory()))
                && (filter.getPlatform() == null || game.getPlatform().equalsIgnoreCase(filter.getPlatform()))
                && (game.getPrice() >= filter.getMinPrice() && game.getPrice() <= filter.getMaxPrice()));
    }
}
