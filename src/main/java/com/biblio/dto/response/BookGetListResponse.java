package com.biblio.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookGetListResponse {
    private Long id;
    private String title;
    private String description;
    private double sellingPrice;

    private CategoryResponse category;
    private BookMetadataResponse metadata;
    private double reviewRate;
}
