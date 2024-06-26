package com.phumlanidev.techhivestore.dto;

import lombok.*;

@Data
public final class CartDTO {

  private Long cartId;
  private Long userId; // foreign key reference
  private double totalPrice;
}
