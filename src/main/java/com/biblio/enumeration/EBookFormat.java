package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EBookFormat {
    PAPERBACK("Bìa mềm"),
    HARDCOVER("Bìa cứng");

    private final String bookFormat;

    EBookFormat(String bookFormat) {
        this.bookFormat = bookFormat;
    }

    public String getDescription() {
        return bookFormat;
    }

}
