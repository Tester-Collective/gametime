package id.ac.ui.cs.advprog.gametime.dto;

import id.ac.ui.cs.advprog.gametime.model.Cart;
import id.ac.ui.cs.advprog.gametime.model.Game;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class OrderDto {
    private String cartId;
    private Map<String , Integer> gameQuantity = new HashMap<>();
    private LocalDateTime orderDate;
    private String orderStatus;
}
