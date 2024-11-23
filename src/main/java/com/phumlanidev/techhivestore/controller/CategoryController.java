package com.phumlanidev.techhivestore.controller;


import com.phumlanidev.techhivestore.auth.ResponseDto;
import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.service.impl.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Comment: this is the placeholder for documentation.
 */
@RestController
@RequestMapping(path = "/api/v1/category", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CategoryController {

  private final CategoryService categoryService;

  /**
   * Comment: this is the placeholder for documentation.
   */
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
    categoryService.createCategory(categoryDto);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(new ResponseDto(Constant.STATUS_CODE_CREATED, "Category created successfully"));
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @PutMapping("/update/{categoryId}")
  public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable Long categoryId,
                                                    @RequestBody CategoryDto categoryDto) {
    CategoryDto updateCategory = categoryService.updateCategory(categoryId, categoryDto);
    return ResponseEntity.ok(updateCategory);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @GetMapping("/find/{categoryId}")
  public ResponseEntity<CategoryDto> findCategoryById(@Valid @PathVariable Long categoryId) {
    CategoryDto category = categoryService.getCategoryById(categoryId);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(category);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @DeleteMapping("/delete/{categoryId}")
  public ResponseEntity<ResponseDto> deleteCategory(@PathVariable Long categoryId) {
    categoryService.deleteCategoryById(categoryId);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new ResponseDto(Constant.STATUS_CODE_ok, Constant.MESSAGE_200));
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    List<CategoryDto> categories = categoryService.findAllcategories();
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(categories);
  }

}
