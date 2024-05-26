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
        Optional<File> file = fileRepository.findByName(fileName);
        String filePath = file.orElseThrow().getFilePath();
        return Files.readAllBytes(new java.io.File(filePath).toPath());
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

    @Override
    public File uploadImageToFileSystem(MultipartFile file) throws IOException {
        Date date = new Date();
        File image = new File();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String originalFileName = file.getOriginalFilename();
            byte[] fileTimeStampBytes = Long.toString(date.getTime()).getBytes(StandardCharsets.UTF_8);
            byte[] fileNameBytes = originalFileName.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(fileTimeStampBytes);
            messageDigest.update(fileNameBytes);
            byte[] digest = messageDigest.digest();

            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String name = bytesToHex(digest) + fileExtension;
            String folderPath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
            String path = folderPath + name;

            image.setName(name);
            image.setFilePath(path);
            image.setType(file.getContentType());
            fileRepository.save(image);
            file.transferTo(new java.io.File(path));

            if (image.getFilePath() != null) {
                return image;
            }
            return null;

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
