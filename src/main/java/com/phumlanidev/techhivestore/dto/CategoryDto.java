package com.phumlanidev.techhivestore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Comment: this is the placeholder for documentation.
 */
@Data
public class CategoryDto {

  @NotBlank(message = "Category name must not be blank")
  private String categoryName;
  private String description;

}
