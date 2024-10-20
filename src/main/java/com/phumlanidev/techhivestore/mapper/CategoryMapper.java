package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  public Category toEntity(CategoryDto dto, Category category) {
    category.setCategoryName(dto.getCategoryName());
    return category;
  }

  public CategoryDto toDTO(Category entity, CategoryDto dto) {
    dto.setCategoryName(entity.getCategoryName());
    dto.setDescription(entity.getDescription());
    return dto;
  }
}
