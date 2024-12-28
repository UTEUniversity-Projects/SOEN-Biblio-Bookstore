package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EBookTemplateStatus {

    COMING_SOON("Sắp ra mắt"),
    ON_SALE("Đang mở bán"),
    OUT_OF_STOCK("Hết hàng"),
    STOP_SELLING("Ngừng kinh doanh");

    private final String description;

    EBookTemplateStatus(String value) {
        this.description = value;
    }

    public String getStatusStyle() {
        switch (this) {
            case COMING_SOON:
                return "coming_soon";
            case ON_SALE:
                return "on_sale";
            case OUT_OF_STOCK:
                return "out_of_stock";
            case STOP_SELLING:
                return "stop_selling";
            default:
                return "";
        }
    }

}
