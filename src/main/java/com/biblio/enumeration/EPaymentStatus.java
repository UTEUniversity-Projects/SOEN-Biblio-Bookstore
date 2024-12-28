package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EPaymentStatus {
    PENDING("Pending"),
    CANCELED("Canceled"),
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    FAILED("Failed");

    private final String displayName;

    EPaymentStatus(String displayName) {
        this.displayName = displayName;
    }

}
