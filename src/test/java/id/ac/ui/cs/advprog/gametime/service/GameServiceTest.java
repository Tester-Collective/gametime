//package id.ac.ui.cs.advprog.gametime.service;
//
//import id.ac.ui.cs.advprog.gametime.model.Game;
//import id.ac.ui.cs.advprog.gametime.model.User;
//import id.ac.ui.cs.advprog.gametime.repository.GameRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.*;
//
//@ExtendWith({MockitoExtension.class})
//class GameServiceTest {
//    @Mock
//    GameRepository gameRepository;
//
//    @InjectMocks
//    GameServiceImpl gameService;
//
//    Game game;
//    Game game2;
//
//    List<Game> gameList;
//
//    User user;
//
//    @BeforeEach
//    void setUp() {
//        gameList = new ArrayList<>();
//
//        user = new User();
//        user.setUserID(UUID.fromString("eb558e9f-1c39-469e-8860-71af6af63bd6"));
//        user.setUsername("kaylasoraya");
//
//        game = new Game();
//        game.setId(UUID.fromString("635efa62-9375-454e-ab7c-7dcf17f74dd6"));
//        game.setTitle("CS:GO");
//        game.setPrice(215000);
//        game.setSeller(user);
//
//        game2 = new Game();
//        game2.setId(UUID.fromString("635efa62-9375-454e-ab7c-7dcf17f74dd7"));
//        game2.setTitle("Dota 2");
//        game2.setSeller(user);
//        game2.setPrice(200000);
//
//        gameList = new ArrayList<>();
//        gameList.add(game);
//        gameList.add(game2);
//    }
//
//    @Test
//    void testGetGameById_Positive() {
//        when(gameRepository.findById(UUID.fromString("635efa62-9375-454e-ab7c-7dcf17f74dd6"))).thenReturn(java.util.Optional.ofNullable(game));
//        Game result = gameService.getGameById("635efa62-9375-454e-ab7c-7dcf17f74dd6");
//        assertEquals(game, result);
//    }
//
//    @Test
//    void testGetGameById_Negative() {
//        when(gameRepository.findById(UUID.fromString("635efa62-9375-454e-ab7c-7dcf17f74dd7"))).thenReturn(java.util.Optional.ofNullable(null));
//        Game result = gameService.getGameById("635efa62-9375-454e-ab7c-7dcf17f74dd7");
//        assertNull(result);
//    }
//
//    @Test
//    void testDeleteGameById_Positive() {
//        gameService.deleteGameById(game.getId().toString());
//        verify(gameRepository, times(1)).deleteById(game.getId());
//    }
//
//    @Test
//    void testDeleteGameById_Negative() {
//        gameService.deleteGameById("eb558e9f-1c39-469e-8860-71af6af63bd7");
//        verify(gameRepository, times(1)).deleteById(UUID.fromString("eb558e9f-1c39-469e-8860-71af6af63bd7"));
//    }
//
//    @Test
//    void testSaveGame() {
//        when(gameRepository.save(game)).thenReturn(game);
//        Game result = gameService.saveGame(game);
//        assertEquals(game, result);
//    }
//
//    @Test
//    void testGetAllGames() {
//        when(gameRepository.findByOrderByTitle()).thenReturn(gameList);
//        List<Game> result = gameService.getAllGames();
//        assertEquals(gameList, result);
//    }
//
//    @Test
//    void testFindGamesBySeller_Negative() {
//        User user2 = new User();
//        user2.setUserID(UUID.fromString("eb558e9f-1c39-469e-8860-71af6af63bd7"));
//        user2.setUsername("fiardiel");
//
//        when(gameRepository.findGamesBySeller(user2)).thenReturn(gameList);
//        List<Game> result = gameService.findGamesBySeller(user2);
//        assertEquals(gameList, result);
//    }
//
//    @Test
//    void testFindGamesBySeller_Positive() {
//        when(gameRepository.findGamesBySeller(user)).thenReturn(gameList);
//        List<Game> result = gameService.findGamesBySeller(user);
//        assertEquals(gameList, result);
//    }
//
//    @Test
//    void getAllGames() {
//        gameList.sort(Comparator.comparing(Game::getTitle));
//
//        when(gameRepository.findByOrderByTitle()).thenReturn(gameList);
//        List<Game> result = gameService.getAllGames();
//        assertEquals(gameList, result);
//    }
//
//    @Test
//    void testDecreaseStock() {
//        game.setStock(10);
//        when(gameRepository.save(game)).thenReturn(game);
//        Game result = gameService.decreaseStock(game, 5);
//        assertEquals(5, result.getStock());
//    }
//}
