package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Category;
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

    @GetMapping("")
    public String home(Model model,
                       @RequestParam(required = false) Category category) {
        List<Game> games;
        games = filterService.filterGame(null, null, null, 0, 0);
        model.addAttribute("games", games);
        model.addAttribute("reviewService", reviewService);
        return "home";
    }
}
