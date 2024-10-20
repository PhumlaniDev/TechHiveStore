package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {


  public Category toEntity(CategoryDto categoryDto) {
    Category category = new Category();
    category.setCategoryName(categoryDto.getCategoryName());
    return category;
  }


  public CategoryDto toDTO(Category entity) {
    CategoryDto categoryDTO = new CategoryDto();
    categoryDTO.setCategoryName(entity.getCategoryName());
    return categoryDTO;
  }
}
