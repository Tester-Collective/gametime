package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.dto.GameDto;
import id.ac.ui.cs.advprog.gametime.model.Image;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.CategoryService;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import id.ac.ui.cs.advprog.gametime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import id.ac.ui.cs.advprog.gametime.service.ImageService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/game/seller")
public class SellerGameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    private static final String SELLER_GAME_PAGE = "redirect:/game/seller";

    @GetMapping("")
    public String index(Model model) {
        User seller = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        List<Game> soldGames = gameService.findGamesBySeller(seller);
        model.addAttribute("games", soldGames);
        model.addAttribute("seller", seller);
        return "game/seller/index";
    }

    @GetMapping("/sell")
    public String sellGamePage(Model model) {
        GameDto gameDto = new GameDto();
        List<Category> categories = categoryService.findAll();

        model.addAttribute("gameDto", gameDto);
        model.addAttribute("categories", categories);
        model.addAttribute("seller", userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()));
        return "game/seller/sell";
    }

    @PostMapping("/sell")
    public String sellGamePost(@ModelAttribute GameDto gameDto, @RequestParam("image") MultipartFile image) throws IOException, NoSuchAlgorithmException {
        Image image1 = imageService.uploadImage(image);

        Game game = new Game();
        game.setTitle(gameDto.getTitle());
        game.setDescription(gameDto.getDescription());
        game.setPrice(gameDto.getPrice());
        game.setSeller(userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()));
        game.setCategory(gameDto.getCategory());
        game.setStock(gameDto.getStock());
        game.setPlatform(gameDto.getPlatform());
        game.setImageName(image1.getName());

        gameService.addGame(game);
        return SELLER_GAME_PAGE;
    }

    @PostMapping("/delete/{id}")
    public String deleteGamePost(@PathVariable String id) {
        Game game = gameService.getGameById(id);
        imageService.deleteImage(game.getImageName());
        gameService.deleteGameById(game.getId().toString());
        return SELLER_GAME_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String editGamePage(@PathVariable String id, Model model) {
        Game game = gameService.getGameById(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("gameDto", new GameDto());
        model.addAttribute("game", game);
        model.addAttribute("categories", categories);
        return "game/seller/edit";
    }

    @PostMapping("/edit/{id}")
    public String editGamePost(@ModelAttribute GameDto gameDto, @PathVariable String id) {
        Game game = gameService.getGameById(id);
        Game editedGame = new Game();
        editedGame.setTitle(gameDto.getTitle());
        editedGame.setDescription(gameDto.getDescription());
        editedGame.setPrice(gameDto.getPrice());
        editedGame.setCategory(game.getCategory());
        editedGame.setStock(gameDto.getStock());
        editedGame.setPlatform(gameDto.getPlatform());
        editedGame.setSeller(game.getSeller());
        editedGame.setImageName(game.getImageName());
        editedGame.setId(UUID.fromString(id));

        gameService.updateGame(id, editedGame);
        return SELLER_GAME_PAGE;
    }
}