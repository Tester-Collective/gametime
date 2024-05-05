package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;

import id.ac.ui.cs.advprog.gametime.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterServiceImpl implements FilterService {
    @Autowired
    private FilterRepository filterRepository;

    public List<Game> filterGame(String keyword, Category category, String platform, int minPrice, int maxPrice) {
        List<Game> games = new ArrayList<>();

        if (category != null) {
            List<Game> temp = filterRepository.findByCategory(category);
            games.addAll(temp);
        }

        if (platform != null) {
            List<Game> temp = filterRepository.findByPlatform(platform);
            for (Game game : temp) {
                if (!games.contains(game)) {
                    games.add(game);
                }
            }
        }

        if (minPrice != 0 && maxPrice != Integer.MAX_VALUE) {
            List<Game> temp = filterRepository.findByPrice(minPrice, maxPrice);
            for (Game game : temp) {
                if (!games.contains(game)) {
                    games.add(game);
                }
            }
        }

        if (keyword != null) {
            List<Game> temp = filterRepository.findByTitle(keyword);
            for (Game game : temp) {
                if (!games.contains(game)) {
                    games.add(game);
                }
            }
        }
        return games;
    }
}
