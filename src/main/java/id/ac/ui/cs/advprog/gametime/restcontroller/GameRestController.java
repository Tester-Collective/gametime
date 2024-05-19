package id.ac.ui.cs.advprog.gametime.restcontroller;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.service.FilterService;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @Autowired
    private FilterService filterService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("{id}")
    public Game getGameById(@PathVariable String id) {
        return gameService.getGameById(id);
    }

    @GetMapping("")
    public List<Game> fetchAll(@Param("keyword") String keyword) {
        return filterService.filterGame(keyword, null, null, 0, Integer.MAX_VALUE);
    }

    @GetMapping("reviews/{id}")
    public List<Review> getReviewsByGameId(@PathVariable String id) {
        return reviewService.findReviewsByGameId(UUID.fromString(id));
    }

    @GetMapping("reviews/rating/{id}")
    public float getAverageRatingByGameId(@PathVariable String id) {
        return reviewService.calculateGameRatingAverage(UUID.fromString(id));
    }

    @GetMapping("reviews/count/{id}")
    public int getReviewCountByGameId(@PathVariable String id) {
        return reviewService.getReviewCountByGame(UUID.fromString(id));
    }
}