package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EPromotionStatus {
    USED("Đã sử dụng"),
    NOT_USE("Chưa sử dụng");

    // Getter for description
    private final String description;

    // Constructor
    EPromotionStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name();
    }
}

