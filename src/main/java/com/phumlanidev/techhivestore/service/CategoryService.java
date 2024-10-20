package com.phumlanidev.techhivestore.service;


import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.mapper.CategoryMapper;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.model.Users;
import com.phumlanidev.techhivestore.repository.CategoryRepository;
import com.phumlanidev.techhivestore.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> comment </p>.
 */
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;
  private final UsersRepository usersRepository;

  public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, UsersRepository usersRepository) {
    this.categoryRepository = categoryRepository;
      this.categoryMapper = categoryMapper;
      this.usersRepository = usersRepository;
  }


  @Transactional
  public Category saveCategory(Category category) {
    return categoryRepository.save(category);
  }

  // create a method that will create a category, the method should be annotated with @Transactional, should be created by an admin, should not save a category with null values, should also check if the method already exists
  @Transactional
  public CategoryDto createCategory(Category category) {
    if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
      throw new IllegalArgumentException("Category name cannot be null or empty");
    }
    if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
      throw new RuntimeException("Category already exists");
    }
    Users createdBy = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    category.setCreatedBy(createdBy);
    category.setCreatedDate(LocalDateTime.now());
    return categoryMapper.toDTO(categoryRepository.save(category));
  }

  // create a method that will update a category and should be updated by an admin
    @Transactional
    public CategoryDto updateCategory(Category category) {
        Users updatedBy = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        category.setUpdatedBy(updatedBy);
        category.setUpdatedDate(LocalDateTime.now());
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    // create a method that will return a list of all categories
    @Transactional
    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDTO).collect(Collectors.toList());
    }

    // create a method that will return a category by id
    @Transactional
    public CategoryDto findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // create a method that will delete a category by an admin
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }


}
