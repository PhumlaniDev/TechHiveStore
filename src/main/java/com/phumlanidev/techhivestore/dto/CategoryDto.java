package com.phumlanidev.techhivestore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * <p> comment </p>.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

  @NotBlank(message = "Category name must not be blank")
  private String categoryName;
  private String description;

}
