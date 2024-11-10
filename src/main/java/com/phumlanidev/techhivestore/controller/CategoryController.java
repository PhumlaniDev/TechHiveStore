package com.phumlanidev.techhivestore.controller;


import com.phumlanidev.techhivestore.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Comment: this is the placeholder for documentation.
 */
@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;
}
