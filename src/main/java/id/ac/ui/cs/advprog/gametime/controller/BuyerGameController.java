package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/game/buyer")
public class BuyerGameController {
    @Autowired
    private GameService gameService;

    @GetMapping("")
    public String index(Model model) {
        List<Game> games = gameService.getAllGames();
        model.addAttribute("games", games);
        return "game/buyer/index";
    }
}
