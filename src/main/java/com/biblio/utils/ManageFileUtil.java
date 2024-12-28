package com.biblio.utils;

import com.biblio.constants.StoredFileConstants;

import javax.servlet.ServletContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ManageFileUtil {

    public static Boolean deleteFileAvatar(String filePath, String type) {
        try {
            if (filePath.equals(getDefaultAvatar(type))) {
                return true;
            }

            filePath = filePath.replace("/", "\\");
            filePath = StoredFileConstants.LOCAL_STORED + filePath;

            System.out.println(filePath);

            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getDefaultAvatar(String type) {
        return switch (type) {
            case "author" -> StoredFileConstants.AUTHOR_DEFAULT_AVA;
            case "translator" -> StoredFileConstants.TRANSLATOR_DEFAULT_AVA;
            case "publisher" -> StoredFileConstants.PUBLISHER_DEFAULT_AVA;
            case "category" -> StoredFileConstants.CATEGORY_DEFAULT_AVA;
            case "subCategory" -> StoredFileConstants.SUB_CATEGORY_DEFAULT_AVA;
            case "product" -> StoredFileConstants.PRODUCT_DEFAULT_AVA;
            default -> "";
        };
    }
}