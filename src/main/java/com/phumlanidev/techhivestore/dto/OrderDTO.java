package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.enums.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Data
public class OrderDTO {

  private Long orderId;
  private UUID orderNumber;
  private Long userId; // foreign key reference
  private LocalDateTime orderCreatedDate;
  private OrderStatus status;
  private double totalPrice;
  private PaymentStatus paymentStatus;
}
