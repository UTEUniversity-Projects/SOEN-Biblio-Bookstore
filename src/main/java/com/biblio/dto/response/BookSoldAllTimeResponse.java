package com.biblio.dto.response;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class BookSoldAllTimeResponse {
    private long id;
    private String srcImg;
    private String title;
    private String category;
    private Long countSold;
    private Long countInStock;
}

