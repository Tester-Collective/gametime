package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.File;
import id.ac.ui.cs.advprog.gametime.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface ImageService {

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException;

    public void deleteImage(String fileName);

    public Image uploadImageToFileSystem(MultipartFile file) throws IOException;

}
