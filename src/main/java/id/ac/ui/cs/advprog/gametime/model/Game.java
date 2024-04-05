package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class Game {
    private String sellerId;
    private String id;
    private String title;
    private Set<String> category;
    private String description;
    private int price;
    private int stock;
    private String imageLink;
    private Set<String> reviewIdSet;
}
