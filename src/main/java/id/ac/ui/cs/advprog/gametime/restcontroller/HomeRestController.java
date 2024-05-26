package id.ac.ui.cs.advprog.gametime.restcontroller;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeRestController {

    private final FilterService filterService;

    @GetMapping("/platform")
    public CompletableFuture<List<Game>> filterGamesByPlatformOrderByTitle(@RequestParam String platform) {
        if (platform.equals("Platform")) {
            return CompletableFuture.completedFuture(filterService.getGamesTop6OrderByRating());
        } else {
            List<Game> gamesByPlatformOrderByTitle = filterService.getGamesByPlatformOrderByRating(platform);
            return CompletableFuture.completedFuture(gamesByPlatformOrderByTitle);
        }
    }

    @GetMapping("")
    public CompletableFuture<List<Game>> filterGames() {
        List<Game> games = filterService.getAllGamesWithConstraint();
        return CompletableFuture.completedFuture(games);
    }
}
