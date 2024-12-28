package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum ENotificationStatus {
    NOT_SEEN("Chưa xem"),
    VIEWED("Đã xem");

    private final String displayName;

    ENotificationStatus(String displayName) {
        this.displayName = displayName;
    }

}
