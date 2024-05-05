package com.naukma.shopspringboot.order.status;

public enum OrderStatus {
    COMPLETED("Completed"),
    PENDING("Pending"),
    CANCELLED("Cancelled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
