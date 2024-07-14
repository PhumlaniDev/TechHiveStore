package com.phumlanidev.techhivestore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDTO {

  @NotBlank(message = "Category name must not be blank")
  private String categoryName;
  private String description;
}
