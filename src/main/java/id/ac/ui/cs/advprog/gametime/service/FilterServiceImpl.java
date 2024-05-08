package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;

import id.ac.ui.cs.advprog.gametime.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class FilterServiceImpl implements FilterService {
    @Autowired
    private FilterRepository filterRepository;

    public List<Game> filterGame(String keyword, Category category, String platform, int minPrice, int maxPrice) {
        Set<Game> gamesSet = new HashSet<>();

        if (category != null) {
            gamesSet.addAll(filterRepository.findByCategory(category));
        }

        if (platform != null) {
            gamesSet.addAll(filterRepository.findByPlatform(platform));
        }

        if (minPrice != 0 && maxPrice != Integer.MAX_VALUE) {
            gamesSet.addAll(filterRepository.findByPriceBetween(minPrice, maxPrice));
        }

        if (keyword != null) {
            gamesSet.addAll(filterRepository.findByTitle(keyword));
        }

        return new ArrayList<>(gamesSet);
    }

}