package com.phumlanidev.techhivestore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * <p> comment </p>.
 */
@Data
public class CategoryDto {

    @NotBlank(message = "Category name must not be blank")
    private String categoryName;
    private String description;

}
