package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.Image;
import id.ac.ui.cs.advprog.gametime.repository.ImageRepository;
import id.ac.ui.cs.advprog.gametime.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image uploadImage(MultipartFile file) {
        Date date = new Date();
        Image image = new Image();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            String originalFileName = file.getOriginalFilename();
            byte[] fileTimeStampBytes = Long.toString(date.getTime()).getBytes(StandardCharsets.UTF_8);
            byte[] fileNameBytes = originalFileName.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(fileTimeStampBytes);
            messageDigest.update(fileNameBytes);
            byte[] digest = messageDigest.digest();

            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            image.setName(bytesToHex(digest) + fileExtension);
            image.setType(file.getContentType());
            image.setImageData(ImageUtil.compressImage(file.getBytes()));
            imageRepository.save(image);

            if (image.getImageData() != null) {
                return image;
            }
            return null;
        } catch (Exception e) {
            // should never happen
            return null;
        }
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

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
