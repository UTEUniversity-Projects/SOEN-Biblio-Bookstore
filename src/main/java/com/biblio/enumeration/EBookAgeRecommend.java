package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EBookAgeRecommend {
    ALL_AGES("Mọi lứa tuổi"),
    UNDER_3_YEARS_OLD("Dưới 3 tuổi"),
    THREE_TO_FIVE_YEARS_OLD("Từ 3 đến 5 tuổi"),
    SIX_TO_FIFTEEN_YEARS_OLD("Từ 6 đến 15 tuổi"),
    SIXTEEN_TO_EIGHTEEN_YEARS_OLD("Từ 16 đến 18 tuổi"),
    OVER_18_YEARS_OLD("Trên 18 tuổi");

    private final String bookAgeRecommend;

    EBookAgeRecommend(String bookAgeRecommend) {
        this.bookAgeRecommend = bookAgeRecommend;
    }

    public String getDescription() {
        return bookAgeRecommend;
    }

}
