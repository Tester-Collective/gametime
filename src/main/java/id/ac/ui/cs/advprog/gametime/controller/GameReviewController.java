package id.ac.ui.cs.advprog.gametime.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game/buyer/details")
public class GameReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    private static final String GAME_DETAILS_PAGE = "redirect:/game/buyer/details";

    @GetMapping("/{gameId}")
    public String gameDetails(Model model, @PathVariable String gameId){
        model.addAttribute("reviews", reviewService.findReviewsByGameId(UUID.fromString(gameId)));
        model.addAttribute("game", gameService.getGameById(gameId));
        model.addAttribute("reviewCountByGame", reviewService.getReviewCountByGame(UUID.fromString(gameId)));
        model.addAttribute("avgRatingByGame", reviewService.calculateGameRatingAverage(UUID.fromString(gameId)));
        return "game/buyer/details";
    }

    @PostMapping("/{gameId}/addReview")
    public String addReviewPost(@ModelAttribute("review") Review reviewInput, @PathVariable String gameId){
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Game game = gameService.getGameById(gameId);

        reviewInput.setGame(game);
        reviewInput.setUser(user);
        reviewInput.setReviewDate(LocalDateTime.now());

        reviewService.addReview(reviewInput);

        return "redirect:/game/buyer/details/" + gameId;
    }

    @GetMapping("/{gameId}/addReview")
    public String addReviewPage(Model model, @PathVariable String gameId){
        Review review = new Review();
        review.setGame(gameService.getGameById(gameId));
        model.addAttribute("review", review);
        return "game/buyer/review/addReview";

    }

    @PostMapping("/{gameId}/deleteReview/{reviewId}")
    public String deleteReviewPost(@PathVariable String reviewId, @PathVariable String gameId){
        reviewService.deleteReviewById(UUID.fromString(reviewId));
        return GAME_DETAILS_PAGE;
    }

    @PostMapping("/{gameId}/editReview/{reviewId}")
    public String editReviewPost(@ModelAttribute Review reviewInput, @PathVariable String reviewId, @PathVariable String gameId){
        Review review = reviewService.getReviewById(UUID.fromString(reviewId));
        review.setReviewTitle(reviewInput.getReviewTitle());
        review.setRating(reviewInput.getRating());
        review.setReviewText(reviewInput.getReviewText());

        reviewService.updateReview(UUID.fromString(reviewId), review);
        return GAME_DETAILS_PAGE;
    }

    @GetMapping("/{gameId}/editReview/{reviewId}")
    public String editReviewPage(Model model, @PathVariable String reviewId, @PathVariable String gameId){
        Review review = reviewService.getReviewById(UUID.fromString(reviewId));
        model.addAttribute("review", review);
        return "game/buyer/review/editReview";
    }




}
