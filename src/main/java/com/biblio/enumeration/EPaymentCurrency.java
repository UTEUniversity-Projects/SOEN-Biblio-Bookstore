package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EPaymentCurrency {
    VND("VNĐ"),
    DOLLAR("$");

    private final String displayName;

    EPaymentCurrency(String displayName) {
        this.displayName = displayName;
    }

}
