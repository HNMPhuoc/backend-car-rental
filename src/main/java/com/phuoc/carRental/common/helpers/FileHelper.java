package com.phuoc.carRental.common.helpers;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileHelper {
    public static String generateUniqueFileName(String originalName) {
        String ext = "";
        int idx = originalName.lastIndexOf('.');
        if (idx > 0) ext = originalName.substring(idx);
        return UUID.randomUUID().toString() + ext;
    }

    public static void ensureDirectoryExists(String dir) {
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("Cannot create upload directory: " + dir, e);
            }
        }
    }

    public static void saveFile(MultipartFile file, String fullPath) {
        try {
            Path dest = Paths.get(fullPath);
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + fullPath, e);
        }
    }
}
