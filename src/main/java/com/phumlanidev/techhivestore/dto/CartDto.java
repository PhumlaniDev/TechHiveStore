package com.phumlanidev.techhivestore.dto;

import lombok.Data;

/**
 * Comment: this is the placeholder for documentation.
 */
@Data
public class CartDto {

  private Long cartId;
  private Long userId; // foreign key reference
  private double totalPrice;
}
