package com.weightloss.weightloss_backend.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    /**
     * Save file to ${uploadDir}/products and return the public path (e.g. /files/products/{filename})
     */
    public String saveProductImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        // ensure product folder exists
        Path productDir = Paths.get(System.getProperty("user.dir"), uploadDir, "products");
        Files.createDirectories(productDir);

        // create unique filename with original extension
        String orig = file.getOriginalFilename();
        String ext = "";
        if (orig != null && orig.contains(".")) {
            ext = orig.substring(orig.lastIndexOf('.'));
        }
        String filename = UUID.randomUUID().toString() + ext;
        Path destination = productDir.resolve(filename);

        // save file
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        // return public URL path used by frontend (matches WebMvcConfig mapping)
        return "/files/products/" + filename;
    }
}
