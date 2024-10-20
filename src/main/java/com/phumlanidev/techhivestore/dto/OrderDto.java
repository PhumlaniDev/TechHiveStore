package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.enums.OrderStatus;
import com.phumlanidev.techhivestore.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p> comment </p>.
 */
@Data
public class OrderDto {

    private Long orderId;
    private UUID orderNumber;
    private Long userId; // foreign key reference
    private LocalDateTime orderCreatedDate;
    private OrderStatus status;
    private double totalPrice;
    private PaymentStatus paymentStatus;
}
