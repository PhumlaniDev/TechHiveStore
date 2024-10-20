package com.phumlanidev.techhivestore.service;


import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.exception.CategoryAlreadyExistException;
import com.phumlanidev.techhivestore.mapper.CategoryMapper;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.repository.CategoryRepository;
import com.phumlanidev.techhivestore.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p> comment </p>.
 */
@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;
    private UsersRepository usersRepository;

  // create a method that will create a category, the method should be annotated with @Transactional, should be created by an admin, should not save a category with null values, should also check if the method already exists
  @Transactional
  public void createCategory(CategoryDto categoryDto) {
    Category category = categoryMapper.toEntity(categoryDto, new Category());
    Optional<Category> optionalCategory = categoryRepository.findByCategoryName(categoryDto.getCategoryName());

    optionalCategory.ifPresent(value ->{
      throw new CategoryAlreadyExistException("Category already exist");
    });

    categoryRepository.save(category);

//    if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
//      throw new IllegalArgumentException("Category name cannot be null or empty");
//    }
//    if (categoryRepository.findByCategoryName(category.getCategoryName())) {
//      throw new RuntimeException("Category already exists");
//    }
//    Users createdBy = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//    category.setCreatedBy(createdBy.toString());
//    return categoryMapper.toDTO(categoryRepository.save(category));
  }

  // create a method that will update a category and should be updated by an admin
//    @Transactional
//    public CategoryDto updateCategory(Category category) {
//        Users updatedBy = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        category.setUpdatedBy(updatedBy.toString());
//        return categoryMapper.toDTO(categoryRepository.save(category));
//    }
//
//    // create a method that will return a list of all categories
//    @Transactional
//    public List<CategoryDto> findAllCategories() {
//        return categoryRepository.findAll().stream().map(categoryMapper::toDTO).collect(Collectors.toList());
//    }
//
//    // create a method that will return a category by id
//    @Transactional
//    public CategoryDto findCategoryById(Long categoryId) {
//        return categoryRepository.findById(categoryId)
//                .map(categoryMapper::toDTO)
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
