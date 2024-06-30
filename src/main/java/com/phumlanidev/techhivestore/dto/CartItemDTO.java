package com.phumlanidev.techhivestore.dto;

import lombok.Data;

@Data
public class CartItemDTO {

  private Long cartItemsId;
  private Long cartId; // foreign key reference
  private Long productId; // foreign key reference
  private Integer quantity;
  private Integer price;
}
