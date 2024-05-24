package id.ac.ui.cs.advprog.gametime.dto;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Game;
import id.ac.ui.cs.advprog.gametime.model.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class GameDto {
    private UUID id;
    private String title;
    private String description;
    private String platform;
    private int price;
    private int stock;
    private Category category;
    private MultipartFile image;

    public GameDto(Game game) {
        this.id = game.getId();
        this.category = game.getCategory();
        this.stock = game.getStock();
        this.title = game.getTitle();
        this.description = game.getDescription();
        this.platform = game.getPlatform();
        this.price = game.getPrice();
    }

    public GameDto() {}
}
