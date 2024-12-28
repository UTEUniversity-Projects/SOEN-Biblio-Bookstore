package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EAccountStatus {
    ACTIVE("Hoạt động"),
    INACTIVE("Bị cấm");

    private final String value;

    EAccountStatus(String value) {
        this.value = value;
    }

}
