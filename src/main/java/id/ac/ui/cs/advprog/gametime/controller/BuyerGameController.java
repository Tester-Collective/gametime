package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/game/buyer")
public class BuyerGameController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FilterService filterService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String index(Model model, @Param("keyword") String keyword) {
        List<Game> games = filterService.getGamesByKeyword(keyword);
        model.addAttribute("games", games);
        model.addAttribute("reviewService", reviewService);
        model.addAttribute("user", userService.getLoggedInUser());
        return "game/buyer/index";
    }

}
