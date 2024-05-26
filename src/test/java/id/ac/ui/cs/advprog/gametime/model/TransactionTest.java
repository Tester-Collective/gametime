package id.ac.ui.cs.advprog.gametime.model;
import enums.TransactionStatus;
import id.ac.ui.cs.advprog.gametime.model.state.FailedState;
import id.ac.ui.cs.advprog.gametime.model.state.InitialState;
import id.ac.ui.cs.advprog.gametime.model.state.TransactionState;
import id.ac.ui.cs.advprog.gametime.repository.TransactionRepository;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;
import id.ac.ui.cs.advprog.gametime.service.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    private List<GameInCart> gamesincart = new ArrayList<>();
    private List<Game> games = new ArrayList<>();
    private User user = new User();
    private Order order = new Order();
    private Cart cart = new Cart();
    private GameInCart gameInCart = new GameInCart();
    private Game game = new Game();
    private User seller = new User();

    @BeforeEach
    void setUp() {
        game.setId(UUID.randomUUID());
        game.setSeller(seller);
        game.setTitle("Mock Game");
        game.setDescription("Mock Game Description");
        game.setPrice(50);
        game.setStock(5);
        games.add(game);
        cart.setGames(gamesincart);
        cart.setCustomer(user);
        cart.setCartId(UUID.randomUUID());
        gameInCart.setGame(game);
        gameInCart.setQuantity(1);
        gameInCart.setCart(cart);
        gameInCart.setGameInCartId(UUID.randomUUID());
        gamesincart.add(gameInCart);
        cart.setGames(gamesincart);
        order.setGameQuantity(Map.of(game, 1));
        order.setCart(cart);
        order.setOrderId(UUID.randomUUID());
    }

    @Test
    void testSetTransactionId() {
        Transaction transaction = new Transaction();
        UUID transactionId = UUID.randomUUID();
        transaction.setTransactionId(transactionId);
        assertEquals(transactionId, transaction.getTransactionId());
    }
    @Test
    void testSetUser() {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        assertEquals(user, transaction.getUser());
    }
    @Test
    void testSetOrder() {
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        assertEquals(order, transaction.getOrder());
    }
    @Test
    void testSetStatus() {
        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.FAILED.getValue());
        assertEquals(TransactionStatus.FAILED.getValue(), transaction.getStatus());
    }
    @Test
    void testSetStatusFailed() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, user, order, TransactionStatus.FAILED.getValue());
        assertEquals(TransactionStatus.FAILED.getValue(), transaction.getStatus());
    }
    @Test
    void testSetStatusSuccess() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, user, order, TransactionStatus.SUCCESS.getValue());
        assertEquals(TransactionStatus.SUCCESS.getValue(), transaction.getStatus());
    }
    @Test
    void testNegativeSetStatus() {
        Transaction transaction = new Transaction();
        assertThrows(IllegalArgumentException.class, () -> transaction.setStatus("INVALID_STATUS"));
    }
    @Test
    void testSetTransactionDate() {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate("01-01-2020 00:00:00");
        assertEquals("01-01-2020 00:00:00", transaction.getTransactionDate());
    }
    @Test
    void testGetTotalPrice() {
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        assertEquals(50, transaction.getTotalPrice());
    }
    @Test
    void testConstructor() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, user, order);
        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(user, transaction.getUser());
        assertEquals(order, transaction.getOrder());
        assertEquals(TransactionStatus.FAILED.getValue(), transaction.getStatus());
    }
    @Test
    void testConstructorWithStatus() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, user, order, TransactionStatus.SUCCESS.getValue());
        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(user, transaction.getUser());
        assertEquals(order, transaction.getOrder());
        assertEquals(TransactionStatus.SUCCESS.getValue(), transaction.getStatus());
    }
    @Test
    void testGetTransactionId() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        assertEquals(transactionId, transaction.getTransactionId());
    }
    @Test
    void testGetUser() {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        assertEquals(user, transaction.getUser());
    }
    @Test
    void testGetOrder() {
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        assertEquals(order, transaction.getOrder());
    }
    @Test
    void testGetStatus() {
        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.FAILED.getValue());
        assertEquals(TransactionStatus.FAILED.getValue(), transaction.getStatus());
    }
    @Test
    void testGetTransactionDate() {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate("01-01-2020 00:00:00");
        assertEquals("01-01-2020 00:00:00", transaction.getTransactionDate());
    }
    @Test
    void testGetTransactionDateWithConstructor() {
        Transaction transaction = new Transaction();
        assertNotNull(transaction.getTransactionDate());
    }
    @Test
    void testGetTotalPriceWithEmptyOrder() {
        Transaction transaction = new Transaction();
        Order order = new Order();
        order.setGameQuantity(Map.of());
        transaction.setOrder(order);
        assertEquals(0, transaction.getTotalPrice());
    }
    @Test
    void testSetState(){
        Transaction transaction = new Transaction();
        TransactionState newstate = new InitialState();
        transaction.setState(newstate);
        assertEquals(transaction.getState(),newstate);
    }
    @Test
    void testGetGameQuantityForJson(){
        Transaction transaction = new Transaction();
        Order order = new Order();
        Map<Game,Integer> gameQuantity = Map.of(game,2);
        order.setGameQuantity(gameQuantity);
        transaction.setOrder(order);
        String gamedetail = game.getTitle() + " - " + game.getPlatform();
        Map<String,Integer> jsonResult = Map.of(gamedetail,2);
        assertEquals(transaction.getGameQuantityForJson(),jsonResult);
    }
    @Test
    void testCalculateSellerGameQuantityAndRevenue(){
        Transaction transaction = new Transaction();
        Order order = new Order();
        Map<Game,Integer> gameQuantity = Map.of(game,2);
        order.setGameQuantity(gameQuantity);
        transaction.setOrder(order);
        transaction.setStatus(TransactionStatus.SUCCESS.getValue());
        transaction.calculateSellerGameQuantityAndRevenue(seller);
        String gamedetail = game.getTitle() + " - " + game.getPlatform();
        Integer revenue = game.getPrice() * 2;
        Map<String,Integer> jsonResult = Map.of(gamedetail,2);
        assertEquals(transaction.getSellerGameQuantity(),jsonResult);
        assertEquals(transaction.getSellerRevenue(),revenue);
    }
    @Test
    void testSetSellerGameQuantity(){
        Transaction transaction = new Transaction();
        Map<String,Integer> sellerGameQuantity = Map.of("Mock Game - Mock Platform",2);
        transaction.setSellerGameQuantity(sellerGameQuantity);
        assertEquals(transaction.getSellerGameQuantity(),sellerGameQuantity);
    }
    @Test
    void testSetSellerRevenue(){
        Transaction transaction = new Transaction();
        Integer revenue = 100;
        transaction.setSellerRevenue(revenue);
        assertEquals(transaction.getSellerRevenue(),revenue);
    }
    @Test
    void testProcessState(){
        Transaction transaction = new Transaction();
        TransactionState newstate = new InitialState();
        transaction.setState(newstate);
        Order order = new Order();
        transaction.setUser(user);
        Map<Game,Integer> gameQuantity = Map.of(game,2);
        order.setGameQuantity(gameQuantity);
        transaction.setOrder(order);
        TransactionServiceImpl service = new TransactionServiceImpl();
        transaction.processState(service);
        assertEquals(transaction.getState(),newstate);
    }
}
