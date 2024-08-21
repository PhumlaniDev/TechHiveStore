package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.model.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p> comment </p>.
 */
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
  private Category category;
  private Users createdByUser;
  private Users updatedByUser;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
}
