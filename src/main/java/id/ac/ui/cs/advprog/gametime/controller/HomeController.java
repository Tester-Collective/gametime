package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.service.CategoryService;
import id.ac.ui.cs.advprog.gametime.service.ReviewService;
import org.springframework.ui.Model;
import id.ac.ui.cs.advprog.gametime.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import id.ac.ui.cs.advprog.gametime.service.FilterService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FilterService filterService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String home(Model model) {
        List<Game> games;
        games = filterService.getTopThreeFreeGames();

        List<Game> gamesTop6;
        gamesTop6 = filterService.getGamesTop6OrderByRating();

        model.addAttribute("games", games);
        model.addAttribute("gamesTop6", gamesTop6);
        model.addAttribute("reviewService", reviewService);
        return "home";
    }
}
