package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Setter @Getter
public class Transaction {
    private UUID transactionId;
    private UUID userId;
    private List<Game> games; // for seller to get all order by gameid
    private List<Order> orders; // for buyer to get all order by userid
    public Transaction(UUID transactionId,UUID userId,List<Game> games,List <Order> orders) {
        this.transactionId = transactionId;
        this.userId = userId;
        if (games.isEmpty() == orders.isEmpty()) {
            throw new IllegalArgumentException();
        }else{
            this.games = games;
            this.orders = orders;
        }
    }
}
