package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.model.Category;
import jakarta.validation.constraints.NotBlank;
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
  private Category category;

}
