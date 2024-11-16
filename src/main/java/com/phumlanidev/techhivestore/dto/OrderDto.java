package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.enums.OrderStatus;
import com.phumlanidev.techhivestore.enums.PaymentStatus;
import com.phumlanidev.techhivestore.model.User;
import java.util.List;
import java.util.UUID;
import lombok.Data;

/**
 * Comment: this is the placeholder for documentation.
 */
@Data
public class OrderDto {

  private Long orderId;
  private UUID orderNumber;
  private User userId; // foreign key reference
  private OrderStatus orderStatus;
  private double totalPrice;
  private PaymentStatus paymentStatus;
  private AddressDto address;
  private List<OrderItemDto> items;
}
