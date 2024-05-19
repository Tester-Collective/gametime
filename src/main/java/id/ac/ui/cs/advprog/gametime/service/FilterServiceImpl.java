package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;

import id.ac.ui.cs.advprog.gametime.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilterServiceImpl implements FilterService {
    @Autowired
    private FilterRepository filterRepository;

    public List<Game> filterGame(String keyword, Category category, String platform, int minPrice, int maxPrice) {
        SortedSet<Game> gamesSet = new TreeSet<>(Comparator.comparing(Game::getTitle));

        if (category != null) {
            gamesSet.addAll(filterRepository.findByCategoryOrderByTitle(category));
        }

        if (platform != null) {
            gamesSet.addAll(filterRepository.findByPlatformOrderByTitle(platform));
        }

        if (minPrice != 0 && maxPrice != Integer.MAX_VALUE) {
            gamesSet.addAll(filterRepository.findByPriceBetweenOrderByTitle(minPrice, maxPrice));
        } else if (maxPrice == 0) {
            gamesSet.addAll(filterRepository.findByPriceEqualsOrderByTitle(maxPrice));
        }

        if (keyword != null && !keyword.isEmpty()) {
            gamesSet.addAll(filterRepository.findByTitleIgnoreCaseOrderByTitle(keyword));
        }

        if ((keyword == null || keyword.isEmpty()) && category == null && platform == null && minPrice == 0 && maxPrice == Integer.MAX_VALUE) {
            gamesSet.addAll(filterRepository.findByOrderByTitle());
        }

        return new ArrayList<>(gamesSet);
    }

}