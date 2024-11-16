package com.phumlanidev.techhivestore.service.impl;


import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.exception.CategoryAlreadyExistException;
import com.phumlanidev.techhivestore.mapper.CategoryMapper;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.repository.CategoryRepository;
import com.phumlanidev.techhivestore.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Comment: this is the placeholder for documentation.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

  private CategoryRepository categoryRepository;
  private CategoryMapper categoryMapper;
  private UserRepository userRepository;

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public void createCategory(CategoryDto categoryDto) {
    Category category = categoryMapper.toEntity(categoryDto, new Category());
    Optional<Category> optionalCategory =
            categoryRepository.findByCategoryName(categoryDto.getCategoryName());

    optionalCategory.ifPresent(value -> {
      throw new CategoryAlreadyExistException("Category already exist");
    });

    categoryRepository.save(category);

    //    if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
    //      throw new IllegalArgumentException("Category name cannot be null or empty");
    //    }
    //    if (categoryRepository.findByCategoryName(category.getCategoryName())) {
    //      throw new RuntimeException("Category already exists");
    //    }
    //    Users createdBy = usersRepository
    //    .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    //    category.setCreatedBy(createdBy.toString());
    //    return categoryMapper.toDto(categoryRepository.save(category));
  }

  //    @Transactional
  //    public CategoryDto updateCategory(Category category) {
  //        Users updatedBy = usersRepository
  //        .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
  //        category.setUpdatedBy(updatedBy.toString());
  //        return categoryMapper.toDto(categoryRepository.save(category));
  //    }
  //
  //    // create a method that will return a list of all categories
  //    @Transactional
  //    public List<CategoryDto> findAllCategories() {
  //        return categoryRepository.findAll().stream()
  //        .map(categoryMapper::toDto).collect(Collectors.toList());
  //    }
  //
  //    // create a method that will return a category by id
  //    @Transactional
  //    public CategoryDto findCategoryById(Long categoryId) {
  //        return categoryRepository.findById(categoryId)
  //                .map(categoryMapper::toDto)
  //                .orElseThrow(() -> new RuntimeException("Category not found"));
  //    }
  //
  //    // create a method that will delete a category by an admin
  //    @Transactional
  //    public void deleteCategory(Long categoryId) {
  //        Category category = categoryRepository.findById(categoryId)
  //                .orElseThrow(() -> new RuntimeException("Category not found"));
  //        categoryRepository.delete(category);
  //    }


}
