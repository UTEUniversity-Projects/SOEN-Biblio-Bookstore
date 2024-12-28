package com.biblio.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadFileUtil {

    private static final String UPLOAD_DIR = "images";

    public static String UploadImage(Part filePart, ServletContext servletContext) {
        if (filePart == null || filePart.getSize() == 0) {
            throw new IllegalArgumentException("File is not allowed to be empty!");
        }

        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String absolutePath = servletContext.getRealPath("") + UPLOAD_DIR;
        Path uploadPath = Paths.get(absolutePath);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Error while creating upload directory!\n" + e.getMessage());
            }
        }

        Path filePath = uploadPath.resolve(originalFileName);

        try (InputStream inputStream = filePart.getInputStream()) {
            Files.copy(inputStream, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading image!\n" + e.getMessage());
        }

        return servletContext.getContextPath() + "/" + UPLOAD_DIR + "/" + originalFileName;
    }

    public static String UploadImage(Part filePart, ServletContext servletContext, String dir) {
        if (filePart == null || filePart.getSize() == 0) {
            throw new IllegalArgumentException("File is not allowed to be empty!");
        }

        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String absolutePath = servletContext.getRealPath("") + UPLOAD_DIR + "/" + dir;
        Path uploadPath = Paths.get(absolutePath);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Error while creating upload directory!\n" + e.getMessage());
            }
        }

        Path filePath = uploadPath.resolve(originalFileName);

        try (InputStream inputStream = filePart.getInputStream()) {
            Files.copy(inputStream, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading image!\n" + e.getMessage());
        }

        return servletContext.getContextPath() + "/" + UPLOAD_DIR + "/customer/" + originalFileName;
    }

    public static String UploadImage(Part filePart, ServletContext servletContext, String dir, String fileName) {
        if (filePart == null || filePart.getSize() == 0) {
            throw new IllegalArgumentException("File is not allowed to be empty!");
        }

        // Get the original file name and its extension
        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            fileExtension = originalFileName.substring(i);  // Extract file extension
        }

        // If fileName is provided, use it, otherwise keep the original file name without extension
        String finalFileName = (fileName != null && !fileName.isEmpty()) ? fileName + fileExtension : originalFileName;

        String absolutePath = servletContext.getRealPath("") + UPLOAD_DIR + "/" + dir;
        Path uploadPath = Paths.get(absolutePath);

        // Create the directory if it doesn't exist
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Error while creating upload directory!\n" + e.getMessage());
            }
        }

        // Use finalFileName for the uploaded file
        Path filePath = uploadPath.resolve(finalFileName);

        try (InputStream inputStream = filePart.getInputStream()) {
            Files.copy(inputStream, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading image!\n" + e.getMessage());
        }

        // Return the path with the final file name
        return servletContext.getContextPath() + "/" + UPLOAD_DIR + "/" + dir + "/" + finalFileName;
    }

    public static String UploadImageNonContextPath(Part filePart, ServletContext servletContext, String dir, String fileName) {
        if (filePart == null || filePart.getSize() == 0) {
            throw new IllegalArgumentException("File is not allowed to be empty!");
        }

        // Get the original file name and its extension
        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // If fileName is provided, use it, otherwise keep the original file name without extension
        String finalFileName = (fileName != null && !fileName.isEmpty()) ? fileName : originalFileName;

        String absolutePath = servletContext.getRealPath("") + UPLOAD_DIR + "/" + dir;
        Path uploadPath = Paths.get(absolutePath);

        // Create the directory if it doesn't exist
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Error while creating upload directory!\n" + e.getMessage());
            }
        }

        // Use finalFileName for the uploaded file
        Path filePath = uploadPath.resolve(finalFileName);

        try (InputStream inputStream = filePart.getInputStream()) {
            Files.copy(inputStream, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading image!\n" + e.getMessage());
        }

        // Return the path with the final file name
        return "/" + UPLOAD_DIR + "/" + dir + "/" + finalFileName;
    }
}

