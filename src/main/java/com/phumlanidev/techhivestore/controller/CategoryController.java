package com.phumlanidev.techhivestore.controller;


import com.phumlanidev.techhivestore.dto.CategoryDTO;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> comment </p>.
 */
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping
  public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO) {
    Category category = mapCategoryDTOToEntity(categoryDTO);
    return ResponseEntity.ok(categoryService.saveCategory(category));
  }

  private Category mapCategoryDTOToEntity(CategoryDTO categoryDTO) {
    Category category = new Category();
    category.setCategoryName(categoryDTO.getCategoryName());
    category.setDescription(categoryDTO.getDescription());
    return category;
  }
}
