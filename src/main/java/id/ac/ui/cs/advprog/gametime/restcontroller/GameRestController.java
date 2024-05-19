package id.ac.ui.cs.advprog.gametime.restcontroller;

import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.service.FilterService;
import id.ac.ui.cs.advprog.gametime.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @Autowired
    private FilterService filterService;

    @GetMapping("{id}")
    public Game getGameById(@PathVariable String id) {
        return gameService.getGameById(id);
    }

    @GetMapping("")
    public List<Game> fetchAll(@Param("keyword") String keyword) {
        return filterService.filterGame(keyword, null, null, 0, Integer.MAX_VALUE);
    }
}
