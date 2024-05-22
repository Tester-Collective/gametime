package id.ac.ui.cs.advprog.gametime.controller;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.dto.GameDto;
import id.ac.ui.cs.advprog.gametime.model.Image;
import id.ac.ui.cs.advprog.gametime.model.User;
import id.ac.ui.cs.advprog.gametime.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private ReviewService reviewService;
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
    public String sellGamePost(@ModelAttribute GameDto gameDto, @RequestParam("image") MultipartFile image) {
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

        gameService.saveGame(game);
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
        model.addAttribute("editGame", new GameDto(game));
        model.addAttribute("categories", categories);
        return "game/seller/edit";
    }

    @PostMapping("/edit/{id}")
    public String editGamePost(@ModelAttribute("editGame") GameDto gameDto, @PathVariable String id) {
        User seller = userService.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        Game game = gameService.getGameById(id);
        game.setTitle(gameDto.getTitle());

        if (gameDto.getImage().getSize() != 0) {
            Image uploadedImage = imageService.uploadImage(gameDto.getImage());
            if (game.getImageName() != null) {
                imageService.deleteImage(game.getImageName());
            }
            game.setImageName(uploadedImage.getName());
        }

        game.setStock(gameDto.getStock());
        game.setPrice(gameDto.getPrice());
        game.setCategory(gameDto.getCategory());
        game.setPlatform(gameDto.getPlatform());
        game.setDescription(gameDto.getDescription());
        game.setSeller(seller);

        gameService.saveGame(game);
        return SELLER_GAME_PAGE;
    }

    @GetMapping("/{id}")
    public String gameDetails(Model model, @PathVariable String id){
        model.addAttribute("reviews", reviewService.findReviewsByGameId(UUID.fromString(id)));
        model.addAttribute("game", gameService.getGameById(id));
        model.addAttribute("reviewCountByGame", reviewService.getReviewCountByGame(UUID.fromString(id)));
        model.addAttribute("avgRatingByGame", reviewService.calculateGameRatingAverage(UUID.fromString(id)));
        return "game/seller/details";
    }
}