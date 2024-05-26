package id.ac.ui.cs.advprog.gametime.restcontroller;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.service.FilterService;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/game")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("{id}")
    public Game getGameById(@PathVariable String id) {
        return gameService.getGameById(id);
    }

    @GetMapping("")
    public List<Game> fetchAll() {
        return gameService.getAllGames();
    }

    @GetMapping("/filter")
    public CompletableFuture<List<Game>> filter(@RequestParam("query") String keyword) {
        List<Game> games = gameService.findGamesByKeyword(keyword);
        return CompletableFuture.completedFuture(games);
    }

    @GetMapping("reviews/{id}")
    public List<Review> getReviewsByGameId(@PathVariable String id) {
        return reviewService.findReviewsByGameId(UUID.fromString(id));
    }

    @GetMapping("reviews/rating/{id}")
    public float getAverageRatingByGameId(@PathVariable String id) {
        return gameService.getGameById(id).getAvgRating();
    }

    @GetMapping("reviews/count/{id}")
    public int getReviewCountByGameId(@PathVariable String id) {
        return reviewService.getReviewCountByGame(UUID.fromString(id));
    }
}
