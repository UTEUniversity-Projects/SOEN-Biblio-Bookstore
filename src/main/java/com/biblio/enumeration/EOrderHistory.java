package com.biblio.enumeration;

public enum EOrderHistory {
    PLACED("Đã đặt đơn hàng"),
    CONFIRMED("Xác nhận đơn hàng"),
    WAITING_FOR_SHIPPING("Chờ giao hàng"),
    COMPLETED("Hoàn tất đơn hàng"),
    REVIEWED("Đã đánh giá");

    private final String displayName;

    EOrderHistory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPriority() {
        switch (this) {
            case PLACED:
                return 0;
            case CONFIRMED:
                return 1;
            case WAITING_FOR_SHIPPING:
                return 2;
            case COMPLETED:
                return 3;
            case REVIEWED:
                return 4;
            default:
                return -1;
        }
    }
}

