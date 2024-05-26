package id.ac.ui.cs.advprog.gametime.facade;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.FilterService;
import id.ac.ui.cs.advprog.gametime.service.ReviewFilterService;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HomeFacade {

    private final FilterService filterService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final ReviewFilterService reviewFilterService;

    @Autowired
    public HomeFacade(FilterService filterService, ReviewService reviewService, UserService userService, ReviewFilterService reviewFilterService) {
        this.filterService = filterService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.reviewFilterService = reviewFilterService;
    }

    public List<Game> getTopThreeFreeGames() {
        return filterService.getTopThreeFreeGames();
    }

    public List<Game> getGamesTop6OrderByRating() {
        return filterService.getGamesTop6OrderByRating();
    }

    public List<Review> getTop6Reviews() {
        return reviewFilterService.findTop6ByRatingGreaterThanEqual(4);
    }

    public User getLoggedInUser() {
        return userService.getLoggedInUser();
    }
}
