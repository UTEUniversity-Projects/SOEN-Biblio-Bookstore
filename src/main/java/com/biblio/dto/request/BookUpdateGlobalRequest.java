package com.biblio.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateGlobalRequest {
    String id = "";
    String title = "";
    String sellingPrice = "0";
    String importPrice = "0";
    String quantity = "0";
    List<String> authorIds = new ArrayList<>();
    List<String> translatorIds = new ArrayList<>();
    String subCategoryId = "";
    String conditionCode = "";
    String publisherId = "";
    String formatCode = "";
    String publicationDate = "0001-01-01";
    List<String> languageCodes = new ArrayList<>();
    String ageRecommendCode = "";
    String codeISBN10 = "";
    String codeISBN13 = "";
    String edition = "";
    String hardcover = "0";
    String length = "0";
    String width = "0";
    String height = "0";
    String weight = "0";
    String description = "";
    String mainImage = "";
    List<String> thumbnails = new ArrayList<>();
}
