package com.phumlanidev.techhivestore.enums;

public enum OrderStatus {
    PENDING,       // Order received but not yet processed
    PROCESSING,    // Order is being prepared
    SHIPPED,       // Order has been dispatched
    DELIVERED,     // Order has been delivered to the customer
    CANCELLED,     // Order has been cancelled
    ON_HOLD,       // Order is temporarily paused
    COMPLETED,     // Order has been fully processed and fulfilled
    RETURNED       // Order has been returned by the customer
}
