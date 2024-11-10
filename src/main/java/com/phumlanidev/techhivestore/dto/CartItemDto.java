package com.phumlanidev.techhivestore.dto;

import lombok.Data;

/**
 * Comment: this is the placeholder for documentation.
 */
@Data
public class CartItemDto {

  private Long cartItemsId;
  private Long cartId; // foreign key reference
  private Long productId; // foreign key reference
  private Integer quantity;
  private Integer price;
}
