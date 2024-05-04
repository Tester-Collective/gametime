package id.ac.ui.cs.advprog.gametime.model;

import enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "transaction")
@Setter @Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transactionId;
    @Column(nullable = false)
    private UUID userId;
    @OneToOne
    @JoinColumn(
            name = "order_id",
            referencedColumnName = "orderId",
            nullable = false
    )
    private Order order;
    @Column(nullable = false)
    private String status;
    public Transaction(UUID transactionId,UUID userId,Order order) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.order = order;
        this.status = TransactionStatus.FAILED.getValue();
    }

    public Transaction() {
    }
    public List<Game> getGames() {
        return order.getGames();
    }
}
