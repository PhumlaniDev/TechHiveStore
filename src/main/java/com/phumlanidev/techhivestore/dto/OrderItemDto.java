package com.phumlanidev.techhivestore.dto;

import lombok.Data;

/**
 * Comment: this is the placeholder for documentation.
 */
@Data
public class OrderItemDto {

  private Long orderItemId;
  private Long orderId; // foreign key reference
  private Long productId; // foreign key reference
  private Integer quantity;
  private double price;
}
