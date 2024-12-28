package com.biblio.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailsResponse {
    private Long id;
    private String title;
    private String description;
    private double sellingPrice;
    private String publicationDate;
    private int edition;
    private String codeISBN10;
    private String codeISBN13;
    private String format;
    private int hardcover;
    private String size;
    private double weight;
    private String condition;
    private String recommendedAge;
    private String languages;
    private long quantity;
    private double avgRating;

    private String category;
    private List<String> imageUrls;
    private String publisher;
    private List<AuthorProfileResponse> authors;
    private List<TranslatorProfileResponse> translators;
    private List<ReviewResponse> reviews;
    private int reviewCount;

    private double discount;
}
