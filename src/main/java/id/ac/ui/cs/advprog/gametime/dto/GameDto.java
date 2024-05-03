package id.ac.ui.cs.advprog.gametime.dto;

import id.ac.ui.cs.advprog.gametime.model.Category;
import id.ac.ui.cs.advprog.gametime.model.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class GameDto {
    private String title;
    private String description;
    private String platform;
    private int price;
    private int stock;
    private List<Category> categories;
    private MultipartFile image;
}
