package com.biblio.dto.response;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class CountBookSoldResponse {
    private long id;
    private String srcImg;
    private String title;
    private String category;
    private Long countInStock;
    private Long countSold;

}

