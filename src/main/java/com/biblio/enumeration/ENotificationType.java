package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum ENotificationType {
    ORDER("Order"),
    SUPPORT("Support"),
    SYSTEM("System"),
    OTHER("Other");

    private final String displayName;

    ENotificationType(String displayName) {
        this.displayName = displayName;
    }

}
