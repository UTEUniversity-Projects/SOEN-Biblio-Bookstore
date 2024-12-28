package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EMembership {
    MEMBER("Member"),
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    DIAMOND("Diamond");

    private final String displayName;

    EMembership(String displayName) {
        this.displayName = displayName;
    }

}
