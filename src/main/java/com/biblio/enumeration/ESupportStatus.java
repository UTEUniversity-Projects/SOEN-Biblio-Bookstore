package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum ESupportStatus {
    SENT("Đã gửi"),
    NOT_RESPONDED("Chưa phản hồi"),
    RESPONDED("Đã phản hồi");

    private final String displayName;

    ESupportStatus(String displayName) {
        this.displayName = displayName;
    }

}
