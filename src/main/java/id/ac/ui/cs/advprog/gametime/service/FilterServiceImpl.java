package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Game;

import id.ac.ui.cs.advprog.gametime.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilterServiceImpl implements FilterService {
    @Autowired
    private FilterRepository filterRepository;

    @Override
    public List<Game> getGamesByKeyword(String keyword) {
        return new ArrayList<>(filterRepository.findGamesByGameDeletedAndTitleContainingIgnoreCaseAndStockGreaterThanOrderByTitle(false,  keyword, 0));
    }

    @Override
    public List<Game> getTopThreeFreeGames() {
        return filterRepository.findTop3ByPriceAndGameDeletedEqualsAndStockGreaterThanOrderByAvgRatingDesc(0, false, 0);
    }

    @Override
    public List<Game> getGamesByPlatformOrderByRating(String platform) {
        return new ArrayList<>(filterRepository.findTop6ByGameDeletedAndPlatformAndStockGreaterThanOrderByAvgRatingDesc(false, platform, 0));
    }

    @Override
    public List<Game> getGamesTop6OrderByRating() {
        return new ArrayList<>(filterRepository.findTop6ByGameDeletedAndStockGreaterThanOrderByAvgRatingDesc(false, 0));
    }

    @Override
    public List<Game> getAllGamesWithConstraint() {
        return new ArrayList<>(filterRepository.findByGameDeletedAndStockGreaterThanOrderByTitle(false, 0));
    }
}