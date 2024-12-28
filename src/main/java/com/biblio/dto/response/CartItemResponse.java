package com.biblio.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private Long bookId;
    private String title;
    private String imageUrl;
    private double sellingPrice;
    private long quantity;
    private double subTotal;
    private double salePrice;
}
