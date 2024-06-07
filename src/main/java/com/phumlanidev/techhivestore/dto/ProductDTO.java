package com.phumlanidev.techhivestore.dto;

import lombok.*;

@Data
public class ProductDTO {

  private Long productId;
  private String name;
  private String description;
  private String price;
  private Integer quantity;
  private Long categoryId; // foreign key reference
  private String imageURL;
}
