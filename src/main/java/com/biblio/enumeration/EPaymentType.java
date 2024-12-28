package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EPaymentType {

    BANKING("Chuyển khoản"),
    CASH("Tiền mặt"),
    EWALLET("Ví điện tử");

    private String value;

    EPaymentType(String value) {
        this.value = value;
    }

}
