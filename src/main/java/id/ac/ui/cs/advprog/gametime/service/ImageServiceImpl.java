package id.ac.ui.cs.advprog.gametime.service;

import id.ac.ui.cs.advprog.gametime.model.File;
import id.ac.ui.cs.advprog.gametime.model.Image;
import id.ac.ui.cs.advprog.gametime.repository.FileRepository;
import id.ac.ui.cs.advprog.gametime.repository.ImageRepository;
import id.ac.ui.cs.advprog.gametime.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private FileRepository fileRepository;

    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Image> image = imageRepository.findByName(fileName);
        return ImageUtil.decompressImage(image.orElseThrow().getImageData());
    }

    @Override
    public void deleteImage(String fileName) {
        imageRepository.deleteByName(fileName);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public Image uploadImageToFileSystem(MultipartFile file) throws IOException {
        Date date = new Date();
        Image image = new Image();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                throw new IllegalArgumentException("File must have an original filename");
            }
            byte[] fileTimeStampBytes = Long.toString(date.getTime()).getBytes(StandardCharsets.UTF_8);
            byte[] fileNameBytes = originalFileName.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(fileTimeStampBytes);
            messageDigest.update(fileNameBytes);
            byte[] digest = messageDigest.digest();

            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String name = bytesToHex(digest) + fileExtension;

            image.setName(name);
            image.setType(file.getContentType());
            image.setImageData(ImageUtil.compressImage(file.getBytes()));
            imageRepository.save(image);
            imageRepository.save(image);

            if (image.getImageData() != null) {
                return image;
            }
            return null;

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
