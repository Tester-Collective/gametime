package id.ac.ui.cs.advprog.gametime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.TransactionStatus;
import id.ac.ui.cs.advprog.gametime.model.state.InitialState;
import id.ac.ui.cs.advprog.gametime.model.state.TransactionState;
import id.ac.ui.cs.advprog.gametime.service.TransactionService;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "transactions")
@Setter @Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transactionId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    private User user;
    @JsonIgnore
    @OneToOne
    @JoinColumn(
            name = "order_id",
            referencedColumnName = "orderId",
            nullable = false
    )
    private Order order;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String transactionDate;
    @JsonIgnore
    @Transient
    private TransactionState state = new InitialState();
    @Transient
    private Map<String, Integer> sellerGameQuantity;
    @Transient
    private Integer sellerRevenue;
    public Transaction(UUID transactionId,User user,Order order) {
        this.transactionId = transactionId;
        this.user = user;
        this.order = order;
        this.status = TransactionStatus.FAILED.getValue();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = dateTime.format(format);
        this.transactionDate = formatDateTime;
    }
    public Transaction (UUID transactionId,User user,Order order,String status) {
        this(transactionId, user, order);
        this.setStatus(status);
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = dateTime.format(format);
        this.transactionDate = formatDateTime;
    }

    public void setStatus(String status) {
        if (TransactionStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Transaction() {
        this.status = TransactionStatus.FAILED.getValue();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = dateTime.format(format);
        this.transactionDate = formatDateTime;
    }
    public Integer getTotalPrice() {
        Integer totalPrice = 0;
        for (Game game : order.getGameQuantity().keySet()) {
            totalPrice += game.getPrice() * order.getGameQuantity().get(game);
        }
        return totalPrice;
    }

    public void processState(TransactionService service) {
        state.handle(this,service);
    }
    @JsonProperty("gameQuantity")
    public Map<String, Integer> getGameQuantityForJson() {
        Map<String, Integer> games = new HashMap<>();
        for (Map.Entry<Game, Integer> entry : order.getGameQuantity().entrySet()) {
            Game game = entry.getKey();
            games.put(game.getTitle() + " - " + game.getPlatform(), entry.getValue());
        }
        return games;
    }

    public void calculateSellerGameQuantityAndRevenue(User seller) {
        Map<String, Integer> games = new HashMap<>();
        int revenue = 0;
        for (Map.Entry<Game, Integer> entry : order.getGameQuantity().entrySet()) {
            Game game = entry.getKey();
            if (game.getSeller().equals(seller)) {
                games.put(game.getTitle() + " - " + game.getPlatform(), entry.getValue());
                if (!TransactionStatus.FAILED.getValue().equals(this.status)) {
                    revenue += game.getPrice() * entry.getValue();
                }
            }
        }
        this.sellerGameQuantity = games;
        this.sellerRevenue = revenue;
    }
}
