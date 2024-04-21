package id.ac.ui.cs.advprog.gametime.model;

import enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Setter @Getter
public class Transaction {
    private UUID transactionId;
    private UUID userId;
    private UUID orderId;
    private List<Game> games; // list of game in an order
    private String status;
    public Transaction(UUID transactionId,UUID userId,List<Game> games,UUID orderId,String status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.orderId = orderId;
        this.status = TransactionStatus.FAILED.getValue();
        if (games.isEmpty()) {
            throw new IllegalArgumentException();
        }else{
            this.games = games;
        }
    }
}
