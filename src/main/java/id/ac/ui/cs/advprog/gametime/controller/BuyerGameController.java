package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.service.FilterService;
import id.ac.ui.cs.advprog.gametime.service.FilterServiceImpl;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import id.ac.ui.cs.advprog.gametime.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/game/buyer")
public class BuyerGameController {
    @Autowired
    private GameService gameService;

    @Autowired
    private FilterService filterService;

    @GetMapping("")
    public String index(Model model, @Param("keyword") String keyword) {
        List<Game> games = filterService.filterGame(keyword, null, null, 0, Integer.MAX_VALUE);
        model.addAttribute("games", games);
        return "game/buyer/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable String id, Model model) {
        Game game = gameService.getGameById(id);
        model.addAttribute("game", game);
        return "game/buyer/details";
    }
}
