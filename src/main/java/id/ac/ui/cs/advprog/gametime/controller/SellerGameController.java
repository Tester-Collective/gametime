package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CategoryService;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/game")
public class SellerGameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    private static final String GAME_PAGE = "redirect:/game";

    @GetMapping("")
    public String index(Model model) {
        User seller = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        List<Game> soldGames = gameService.findGamesBySeller(seller);
        model.addAttribute("games", soldGames);
        model.addAttribute("seller", seller);
        return "game/index";
    }

    @GetMapping("/sell")
    public String sellGamePage(Model model) {
        Game game = new Game();
        List<Category> categories = categoryService.findAll();

        model.addAttribute("game", game);
        model.addAttribute("categories", categories);
        model.addAttribute("seller", userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()));
        return "game/sell";
    }

    @PostMapping("/sell")
    public String sellGamePost(@ModelAttribute Game game, @RequestParam("categories") List<Category> categories) {
        game.setSeller(userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()));
        game.setCategories(categories);
        gameService.addGame(game);
        return GAME_PAGE;
    }

    @PostMapping("/delete/{id}")
    public String deleteGamePost(@PathVariable String id) {
        gameService.deleteGameById(id);
        return GAME_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String editGamePage(@PathVariable String id, Model model) {
        Game game = gameService.getGameById(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("game", game);
        model.addAttribute("categories", categories);
        return "game/edit";
    }

    @PostMapping("/edit/{id}")
    public String editGamePost(@PathVariable String id, @ModelAttribute Game game, @RequestParam("categories") List<Category> categories) {
        game.setSeller(userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()));
        game.setCategories(categories);
        gameService.updateGame(id, game);
        return GAME_PAGE;
    }
}