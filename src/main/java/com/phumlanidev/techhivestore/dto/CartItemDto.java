package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.model.Cart;
import com.phumlanidev.techhivestore.model.Product;
import lombok.Data;

/**
 * Comment: this is the placeholder for documentation.
 */
@Data
public class CartItemDto {

  private Cart cart; // foreign key reference
  private Product productId; // foreign key reference
  private Integer quantity;
  private Integer price;
}
