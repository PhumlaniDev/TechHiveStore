package com.phumlanidev.techhivestore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * <p> comment </p>.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

  @NotEmpty(message = "Name must not be blank")
  private String name;
  @NotEmpty(message = "Description must not be blank")
  private String description;
  @NotEmpty(message = "Price is required")
  private String price;
  @NotNull(message = "Quantity must not be null")
  private Integer quantity;
  private String imageURL;
  @NotNull(message = "Category must not be null")
  private CategoryDto category;

}
