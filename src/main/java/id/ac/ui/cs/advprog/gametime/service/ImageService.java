package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public Image uploadImage(MultipartFile file);

    public byte[] downloadImage(String fileName);

    public void deleteImage(String fileName);

}
