package id.ac.ui.cs.advprog.gametime.model;

import enums.OrderStatus;
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
    public Transaction(UUID transactionId,User user,Order order) {
        this.transactionId = transactionId;
        this.user = user;
        this.order = order;
        this.status = TransactionStatus.FAILED.getValue();
    }
    public Transaction (UUID transactionId,User user,Order order, String status) {
        this(transactionId, user, order);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (TransactionStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Transaction() {
    }
    public List<GameInCart> getGames() {
        return order.getCart().getGames();
    }
}
