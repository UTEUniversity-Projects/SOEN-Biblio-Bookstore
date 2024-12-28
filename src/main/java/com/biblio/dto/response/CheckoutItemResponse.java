package com.biblio.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CheckoutItemResponse {
    Long bookTemplateId;
    String title;
    String imagePath;
    int quantity;
    double sellingPrice;
    double totalPrice;
    double discountPercent;

    public void calTotalPrice() {
        this.totalPrice = sellingPrice * quantity * (1 - discountPercent / 100);
    }
}
