package com.phumlanidev.techhivestore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class ProductDTO {

  @NotBlank(message = "Name must not be blank")
  private String name;
  @NotBlank(message = "Description must not be blank")
  private String description;
  @NotBlank(message = "Price must not be blank")
  private String price;
  @NotNull(message = "Quantity must not be null")
  private Integer quantity;
  private String imageURL;
  @NotNull(message = "Category must not be null")
  private CategoryDTO category;
}
