import id.ac.ui.cs.advprog.gametime.controller.SellerGameController;
import id.ac.ui.cs.advprog.gametime.dto.GameDto;
import id.ac.ui.cs.advprog.gametime.model.*;
import id.ac.ui.cs.advprog.gametime.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class SellerGameControllerTest {

    @Mock
    Model model;

    @Mock
    GameService gameService;

    @Mock
    CategoryService categoryService;

    @Mock
    UserService userService;

    @Mock
    ImageService imageService;

    @Mock
    ReviewService reviewService;

    @Mock
    MultipartFile image;

    @InjectMocks
    SellerGameController sellerGameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDisplaySellerGamesWhenPresent() {
        User seller = new User();
        Game game = new Game();
        game.setSeller(seller);
        List<Game> soldGames = new ArrayList<>();
        soldGames.add(game);

        when(userService.getLoggedInUser()).thenReturn(seller);
        when(gameService.findGamesBySeller(seller)).thenReturn(soldGames);

        sellerGameController.index(model);

        verify(model, times(1)).addAttribute("games", soldGames);
        verify(model, times(1)).addAttribute("user", seller);
    }

    @Test
    void shouldDisplayEmptySellerGamesWhenNoneArePresent() {
        User seller = new User();

        when(userService.getLoggedInUser()).thenReturn(seller);
        when(gameService.findGamesBySeller(seller)).thenReturn(new ArrayList<>());

        sellerGameController.index(model);

        verify(model, times(1)).addAttribute("user", seller);
    }

    @Test
    void shouldHandleGamePostSuccessfully() throws IOException {
        GameDto gameDto = new GameDto();
        gameDto.setTitle("Test Game");
        gameDto.setDescription("Test Description");
        gameDto.setPrice(100);
        gameDto.setStock(10);
        gameDto.setPlatform("PC");
        gameDto.setCategory(new Category());
        gameDto.setImage(image);

        when(imageService.uploadImageToFileSystem(image)).thenReturn(new Image());

        sellerGameController.sellGamePost(gameDto, image);

        verify(gameService, times(1)).saveGame(any(Game.class));
    }

    @Test
    void shouldHandleGameEditPostSuccessfully() throws IOException {
        GameDto gameDto = new GameDto();
        gameDto.setTitle("Test Game");
        gameDto.setDescription("Test Description");
        gameDto.setPrice(100);
        gameDto.setStock(10);
        gameDto.setPlatform("PC");
        gameDto.setCategory(new Category());
        gameDto.setImage(image);

        when(imageService.uploadImageToFileSystem(image)).thenReturn(new Image());
        when(gameService.getGameById(anyString())).thenReturn(new Game());

        sellerGameController.editGamePost(gameDto, "1");

        verify(gameService, times(1)).saveGame(any(Game.class));
    }

    @Test
    void shouldHandleGameDeletePostSuccessfully() {
        when(gameService.getGameById(anyString())).thenReturn(new Game());

        sellerGameController.deleteGamePost("1");

        verify(gameService, times(1)).lazyDeleteGame(any(Game.class));
    }
}