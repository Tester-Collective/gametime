package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Review;
import id.ac.ui.cs.advprog.gametime.service.*;
import org.springframework.ui.Model;
import id.ac.ui.cs.advprog.gametime.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FilterService filterService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewFilterService reviewFilterService;

    @GetMapping("")
    public String home(Model model) {
        List<Game> games;
        games = filterService.getTopThreeFreeGames();

        List<Game> gamesTop6;
        gamesTop6 = filterService.getGamesTop6OrderByRating();

        List<Review> reviews;
        reviews = reviewFilterService.findTop6ByRatingGreaterThanEqual(4);


        model.addAttribute("games", games);
        model.addAttribute("gamesTop6", gamesTop6);
        model.addAttribute("reviewService", reviewService);
        model.addAttribute("reviews", reviews);
        model.addAttribute("user", userService.getLoggedInUser());
        return "home";
    }
}
