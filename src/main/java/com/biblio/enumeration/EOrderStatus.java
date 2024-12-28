package com.biblio.enumeration;

import lombok.Getter;

@Getter
public enum EOrderStatus {
    WAITING_PAYMENT("Đang chờ thanh toán"),
    PAID("Đã thanh toán"),
    WAITING_CONFIRMATION("Đang chờ xác nhận"),
    PACKING("Đang đóng gói"),
    SHIPPING("Đang giao hàng"),
    COMPLETE_DELIVERY("Đã hoàn thành"),
    CANCELED("Đã hủy"),
    REQUEST_REFUND("Yêu cầu hoàn tiền"),
    REFUNDED("Đã hoàn tiền");

    // Getter for description
    private final String description;

    // Constructor
    EOrderStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name() + " (" + description + ")";
    }

    public String getStatusStyle() {
        switch (this) {
            case WAITING_PAYMENT:
            case WAITING_CONFIRMATION:
                return "pending";
            case PAID:
            case COMPLETE_DELIVERY:
                return "completed";
            case PACKING:
                return "packing";
            case SHIPPING:
                return "shipping";
            case CANCELED:
                return "canceled";
            case REQUEST_REFUND:
                return "request_refund";
            case REFUNDED:
                return "refunded";
            default:
                return "";
        }
    }

    public int getPriority() {
        switch (this) {
            case WAITING_PAYMENT:
            case WAITING_CONFIRMATION:
                return 6;
            case REQUEST_REFUND:
                return 5;
            case PAID:
            case COMPLETE_DELIVERY:
                return 4;
            case PACKING:
                return 3;
            case SHIPPING:
                return 2;
            case CANCELED:
                return 1;
            case REFUNDED:
                return 0;
            default:
                return -1;
        }
    }
}
