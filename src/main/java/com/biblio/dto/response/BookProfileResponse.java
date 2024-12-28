package com.biblio.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookProfileResponse {
    Long id = 0L;
    String title = "";
    Long sellingPrice = 0L;
    Long importPrice = 0L;
    Integer quantity = 0;
    List<Long> authorIds = new ArrayList<>();
    List<Long> translatorIds = new ArrayList<>();
    Long subCategoryId = 0L;
    String conditionCode = "";
    Long publisherId = 0L;
    String formatCode = "";
    String publicationDate = "0001-01-01";
    List<String> languageCodes = new ArrayList<>();
    String ageRecommendCode = "";
    String codeISBN10 = "";
    String codeISBN13 = "";
    Integer edition = 0;
    Integer hardcover = 0;
    Double length = 0.0D;
    Double width = 0.0D;
    Double height = 0.0D;
    Double weight = 0.0D;
    String description = "";
    List<String> imgUrls = new ArrayList<>();
}
