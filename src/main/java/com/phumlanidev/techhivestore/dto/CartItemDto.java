package com.phumlanidev.techhivestore.dto;

import lombok.Data;

/**
 * <p> comment </p>.
 */
@Data
public class CartItemDto {

  private Long cartItemsId;
  private Long cartId; // foreign key reference
  private Long productId; // foreign key reference
  private Integer quantity;
  private Integer price;
}
