package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EPromotionTargetType {
    BOOK("Sách"),
    CATEGORY("Danh mục"),
    SUBCATEGORY("Danh mục con"),
    WHOLE("Toàn bộ");

    private final String promotionTargetType;

    EPromotionTargetType(String promotionTargetType) {
        this.promotionTargetType = promotionTargetType;
    }

}
