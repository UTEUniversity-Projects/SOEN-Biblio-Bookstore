package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EPromotionTemplateType {
    DISCOUNT("Discount"),
    VOUCHER("Voucher"),
    FREESHIP("Free Shipping");

    private final String description;

    EPromotionTemplateType(String description) {
        this.description = description;
    }

}

