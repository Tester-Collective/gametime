package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.facade.HomeFacade;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final HomeFacade homeFacade;

    @Autowired
    public HomeController(HomeFacade homeFacade) {
        this.homeFacade = homeFacade;
    }

    @GetMapping("")
    public String home(Model model) {
        List<Game> games = homeFacade.getTopThreeFreeGames();
        List<Game> gamesTop6 = homeFacade.getGamesTop6OrderByRating();
        List<Review> reviews = homeFacade.getTop6Reviews();

        model.addAttribute("games", games);
        model.addAttribute("gamesTop6", gamesTop6);
        model.addAttribute("reviews", reviews);
        model.addAttribute("user", homeFacade.getLoggedInUser());

        return "home";
    }
}
