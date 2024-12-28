package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EBookCondition {
    NEW("Mới"),
    USED("Cũ");

    private final String bookCondition;

    EBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    public String getDescription() {
        return bookCondition;
    }

}
