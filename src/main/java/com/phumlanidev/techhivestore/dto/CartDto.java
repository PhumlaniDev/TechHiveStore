package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.model.User;
import java.util.List;
import lombok.Data;

/**
 * Comment: this is the placeholder for documentation.
 */
@Data
public class CartDto {

  private Long cartId;
  private User user; // foreign key reference
  private double totalPrice;
  private List<CartItemDto> cartItemDtoList;
}
