package com.phumlanidev.techhivestore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * <p> comment </p>.
 */
@Data
public class CategoryDTO {

  @NotBlank(message = "Category name must not be blank")
  private String categoryName;
  private String description;

    public CategoryDTO(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public CategoryDTO() {

    }
}
