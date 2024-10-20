package com.phumlanidev.techhivestore.controller;


import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.auth.ResponseDto;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
@AllArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;


  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createCategory(@RequestBody CategoryDto categoryDTO) {
    categoryService.createCategory(categoryDTO);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(Constant.MESSAGE_201, Constant.STATUS_CODE_CREATED));
  }

  private Category mapCategoryDTOToEntity(CategoryDto categoryDTO) {
    Category category = new Category();
    category.setCategoryName(categoryDTO.getCategoryName());
    category.setDescription(categoryDTO.getDescription());
    return category;
  }
}
