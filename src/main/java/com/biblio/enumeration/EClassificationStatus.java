package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EClassificationStatus {
    ACTIVE("Hoạt động"),
    INACTIVE("Không hoạt động");

    private final String status;

    EClassificationStatus(String status) {
        this.status = status;
    }

}
