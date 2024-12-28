package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EReasonReturn {

    DAMAGED("Hư hỏng"),
    NOT_AS_DESCRIBED("Không giống mô tả"),
    FAKE("Hàng giả"),
    NO_NEEDED("Không có nhu cầu nữa");
    
    private String value;
    
    EReasonReturn(String value) {
        this.value = value;
    }
}
