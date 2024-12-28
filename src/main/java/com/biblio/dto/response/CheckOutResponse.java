package com.biblio.dto.response;

import com.biblio.entity.Address;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CheckOutResponse {
    private CustomerDetailResponse customer;
    private List<CheckoutItemResponse> items;
    private ShippingResponse shipping;
    private List<PromotionOrderResponse> promotions;
    private double totalPrice;
    private double finalPrice;

    public void updateTotalPrice() {
        for (CheckoutItemResponse item : items) {
            this.totalPrice += item.getTotalPrice();
        }
    }

    public void updateFinalPrice() {
        this.finalPrice = this.totalPrice + this.shipping.getShippingFee();
        for (PromotionOrderResponse promotion : this.promotions) {
            this.finalPrice -= promotion.getDiscountAmount();
        }
    }

    public void updateShipping() {
        this.shipping = new ShippingResponse();
        this.shipping.setShippingUnit("Giao hàng tiết kiệm");
        this.shipping.setShippingFee(30000);
        AddressResponse firstAddress = customer.getAddresses().stream().findFirst().orElse(null);
        this.shipping.setAddress(firstAddress);
    }
}
