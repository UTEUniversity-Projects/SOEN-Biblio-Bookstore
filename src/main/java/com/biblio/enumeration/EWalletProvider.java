package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EWalletProvider {
    VNPAY("VNPAY"),
    MOMO("Momo");

    private final String displayName;

    EWalletProvider(String displayName) {
        this.displayName = displayName;
    }

}
