package com.biblio.dto.response;

import com.biblio.entity.Promotion;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.enumeration.EPromotionTemplateType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderDetailsManagementResponse {
    private Long id;
    private CustomerResponse customer;
    private ShippingResponse shipping;
    private String orderDate;
    private String note;
    private List<OrderProductResponse> products;
    private double totalPrice;
    private double finalPrice;
    private EOrderStatus status;
    private String statusStyle;
    private String paymentMethod;
    private List<PromotionOrderResponse> promotions;

    public void updateTotalPrice() {
        double calculatedTotalPrice = 0;
        for (OrderProductResponse orderProductResponse : products) {
            calculatedTotalPrice += orderProductResponse.getTotalPrice();
        }
        this.totalPrice = calculatedTotalPrice;
    }

    public void updateFinalPrice() {
        this.finalPrice = this.totalPrice + shipping.getShippingFee();
        for (PromotionOrderResponse promotion : this.promotions) {
            this.finalPrice -= promotion.getDiscountAmount();
        }
        this.finalPrice = Math.max(this.finalPrice, 0);
    }
}
