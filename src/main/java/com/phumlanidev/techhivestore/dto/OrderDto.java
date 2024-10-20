package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.enums.OrderStatus;
import com.phumlanidev.techhivestore.enums.PaymentStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

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
