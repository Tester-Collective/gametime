package id.ac.ui.cs.advprog.gametime.model;

import enums.TransactionStatus;
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
    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    private User user;
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
}
