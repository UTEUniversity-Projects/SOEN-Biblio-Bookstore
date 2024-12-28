package com.biblio.enumeration;

public enum EBookLanguage {
    VIETNAMESE("Tiếng Việt"),
    ENGLISH("Tiếng Anh"),
    FRENCH("Tiếng Pháp"),
    GERMAN("Tiếng Đức"),
    SPANISH("Tiếng Tây Ban Nha"),
    CHINESE("Tiếng Trung"),
    JAPANESE("Tiếng Nhật"),
    KOREAN("Tiếng Hàn"),
    ITALIAN("Tiếng Ý"),
    RUSSIAN("Tiếng Nga"),
    PORTUGUESE("Tiếng Bồ Đào Nha"),
    ARABIC("Tiếng Ả Rập"),
    HINDI("Tiếng Hindi"),
    BENGALI("Tiếng Bengal"),
    TURKISH("Tiếng Thổ Nhĩ Kỳ"),
    INDONESIAN("Tiếng Indonesia"),
    THAI("Tiếng Thái"),
    MALAY("Tiếng Mã Lai"),
    FILIPINO("Tiếng Philippines"),
    DUTCH("Tiếng Hà Lan"),
    SWEDISH("Tiếng Thụy Điển"),
    NORWEGIAN("Tiếng Na Uy"),
    DANISH("Tiếng Đan Mạch"),
    FINNISH("Tiếng Phần Lan"),
    POLISH("Tiếng Ba Lan"),
    GREEK("Tiếng Hy Lạp"),
    HEBREW("Tiếng Do Thái"),
    ROMANIAN("Tiếng Romania"),
    CZECH("Tiếng Séc"),
    HUNGARIAN("Tiếng Hungary");

    private final String bookLanguage;

    EBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public String getDescription() {
        return bookLanguage;
    }
}
