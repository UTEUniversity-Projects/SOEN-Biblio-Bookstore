package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EUserRole {
    CUSTOMER("Khách hàng"),
    STAFF("Nhân viên"),
    OWNER("Chủ");

    private final String displayName;

    EUserRole(String displayName) {
        this.displayName = displayName;
    }

}
