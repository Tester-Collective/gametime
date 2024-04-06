package id.ac.ui.cs.advprog.gametime.model;

import lombok.Getter;

@Getter
public class Filter {
    // Attributes
    private String searchQuery;
    private String category;
    private String platform;
    private int minPrice;
    private int maxPrice;

    // Constructor
    public Filter(String searchQuery, String category, String platform, int minPrice, int maxPrice) {
        this.searchQuery = searchQuery;
        this.category = category;
        this.platform = platform;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}