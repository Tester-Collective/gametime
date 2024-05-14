package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import id.ac.ui.cs.advprog.gametime.service.FilterService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FilterService filterService;

    @GetMapping("")
    public String home(Model model, @Param("keyword") String keyword) {
        List<Game> games = filterService.filterGame(keyword, null, null, 0, Integer.MAX_VALUE);
        model.addAttribute("games", games);
        return "home";
    }
}
