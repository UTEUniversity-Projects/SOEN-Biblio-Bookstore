package com.biblio.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class OrderProductResponse {
    private Long bookTemplateId;
    private String title;
    private String imagePath;
    private int quantity;
    private double sellingPrice;
    private double totalPrice;
    private String publisherName;
    private double discountPercent;

    public void calTotalPrice() {
        this.totalPrice = sellingPrice * quantity * (1 - discountPercent / 100);
    }
}
