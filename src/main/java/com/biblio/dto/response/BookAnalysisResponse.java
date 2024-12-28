package com.biblio.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookAnalysisResponse {
    Long id;
    String title;
    Double avgRating;
    Integer reviewCount;
    String format;
    String sellingPrice;
    String originPrice;
    String condition;
    Integer hardcover;
    String publisher;
    String publicationDate;
    String size;
    Double weight;
    String languages;
    String codeISBN10;
    String codeISBN13;
    String description;
    String category;
    Integer edition;
    String recommendedAge;
    Long quantity;

    String salesCount;
    String booksCount;
    String revenue;

    String salesCountThisMonth;
    Double perSalesCountThisMonth;
    String booksCountThisMonth;
    Double perBooksCountThisMonth;
    String revenueThisMonth;
    Double perRevenueThisMonth;

    List<String> imageUrls;
    List<AuthorProfileResponse> authors;
    List<TranslatorProfileResponse> translators;
    List<ReviewResponse> reviews;
}
