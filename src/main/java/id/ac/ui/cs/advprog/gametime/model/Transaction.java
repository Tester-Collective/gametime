package id.ac.ui.cs.advprog.gametime.model;

import java.util.List;
import java.util.UUID;

public class Transaction {
    private UUID transactionId;
    private UUID userId;
    private List<Game> games;
    private List<Order> orders;
    private String status;
    public Transaction(UUID transactionId,UUID userId,List<Game> games,List <Order> orders, String status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.status = status;
        if (orders.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.orders = orders;
        }
        if (games.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.games = games;
        }
    }
}
