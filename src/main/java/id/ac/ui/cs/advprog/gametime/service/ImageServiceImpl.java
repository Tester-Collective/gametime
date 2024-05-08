package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Image;
import id.ac.ui.cs.advprog.gametime.repository.ImageRepository;
import id.ac.ui.cs.advprog.gametime.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Override
    public Image uploadImage(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        Date date = new Date();
        Image image = new Image();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        byte[] fileTimeStampBytes = Long.toString(date.getTime()).getBytes(StandardCharsets.UTF_8);
        byte[] fileNameBytes = file.getOriginalFilename().getBytes(StandardCharsets.UTF_8);
        messageDigest.update(fileTimeStampBytes);
        messageDigest.update(fileNameBytes);
        byte[] digest = messageDigest.digest();

        image.setName(date.getTime() + file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImageData(ImageUtil.compressImage(file.getBytes()));
        imageRepository.save(image);

        if (image.getImageData() != null) {
            return image;
        }
        return null;
    }

    @Override
    public byte[] downloadImage(String fileName) {
        Optional<Image> dbImageData = imageRepository.findByName(fileName);
        return ImageUtil.decompressImage(dbImageData.get().getImageData());
    }

    @Override
    public void deleteImage(String fileName) {
        imageRepository.deleteByName(fileName);
    }
}
