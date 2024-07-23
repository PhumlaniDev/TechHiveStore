package com.phumlanidev.techhivestore.service;


import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.repository.CategoryRepository;
import org.springframework.stereotype.Service;

/**
 * <p> comment </p>.
 */
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category saveCategory(Category category) {
    return categoryRepository.save(category);
  }

  public Category findCategoryById(Long id) {
    return categoryRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Category not found"));
  }
}
