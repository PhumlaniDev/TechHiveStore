package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.CategoryDTO;
import com.phumlanidev.techhivestore.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper extends AbstractMapper<Category, CategoryDTO> {

  @Override
  public Category toEntity(CategoryDTO dto) {
    Category category = new Category();
    category.setCategoryName(dto.getCategoryName());
    return category;
  }

  @Override
  public CategoryDTO toDTO(Category entity) {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setCategoryName(entity.getCategoryName());
    return categoryDTO;
  }
}
