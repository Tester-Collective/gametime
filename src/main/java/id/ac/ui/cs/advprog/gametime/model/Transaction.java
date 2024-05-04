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
    @ManyToOne
    @JoinColumn(nullable = true, name = "userId")
    private User user;
    @OneToOne
    @JoinColumn(
            name = "order_id",
            referencedColumnName = "orderId",
            nullable = false
    )
    private Cart cart;
    @Column(nullable = false)
    private String status;
    public Transaction(UUID transactionId,User user,Cart cart) {
        this.transactionId = transactionId;
        this.user = user;
        this.cart = cart;
        this.status = TransactionStatus.FAILED.getValue();
    }

    public Transaction() {
    }
    public List<Game> getGames() {
        return cart.getGames();
    }
}
