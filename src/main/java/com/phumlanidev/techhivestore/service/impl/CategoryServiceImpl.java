package com.phumlanidev.techhivestore.service.impl;


import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.exception.category.CategoryAlreadyExistsException;
import com.phumlanidev.techhivestore.exception.category.CategoryNotFoundException;
import com.phumlanidev.techhivestore.mapper.CategoryMapper;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Comment: this is the placeholder for documentation.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public void createCategory(CategoryDto categoryDto) {

    if (categoryDto.getCategoryName() == null || categoryDto.getCategoryName().isEmpty()) {
      throw new IllegalArgumentException("Category name cannot be null or empty");
    }

    if (categoryRepository.findByCategoryName(categoryDto.getCategoryName()).isPresent()) {
      throw new CategoryAlreadyExistsException("Category already exist");
    }

    Category category = categoryMapper.toEntity(categoryDto, new Category());

    Category savedCategory = categoryRepository.save(category);

    categoryMapper.toDto(savedCategory, categoryDto);

  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
    if (categoryDto.getCategoryName() == null || categoryDto.getCategoryName().isEmpty()) {
      throw new IllegalArgumentException("Category name cannot be null or empty");
    }

    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new CategoryNotFoundException("category not found"));

    categoryMapper.toEntity(categoryDto, category);

    Category updatedCategory = categoryRepository.save(category);

    return categoryMapper.toDto(updatedCategory, new CategoryDto());
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public List<CategoryDto> findAllCategories() {

    List<Category> categories = categoryRepository.findAll();

    return categories.stream()
      .filter(category -> category.getCategoryName()
        != null && !category.getCategoryName().isEmpty())
        .map(category -> categoryMapper.toDto(category, new CategoryDto())).toList();
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public CategoryDto getCategoryById(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException("Category not found"));
    return categoryMapper.toDto(category, new CategoryDto());
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public void deleteCategoryById(Long categoryId) {

    categoryRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException("Category not found"));
    categoryRepository.deleteById(categoryId);
  }
}
