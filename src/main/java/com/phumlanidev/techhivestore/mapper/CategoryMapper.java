package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.model.Category;
import org.springframework.stereotype.Component;

/**
 * Comment: this is the placeholder for documentation.
 */
@Component
public class CategoryMapper {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public Category toEntity(CategoryDto dto, Category category) {
    category.setCategoryName(dto.getCategoryName());
    category.setDescription(dto.getDescription());
    return category;
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  public CategoryDto toDto(Category entity, CategoryDto dto) {
    dto.setCategoryName(entity.getCategoryName());
    dto.setDescription(entity.getDescription());
    return dto;
  }
}
