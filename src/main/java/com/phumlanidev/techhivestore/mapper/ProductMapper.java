package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Comment: this is the placeholder for documentation.
 */
@Component
@RequiredArgsConstructor
public class ProductMapper {

  private final CategoryMapper categoryMapper;

  /**
   * Comment: this is the placeholder for documentation.
   */
  public Product toEntity(ProductDto dto, Product product) {
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setQuantity(dto.getQuantity());
    product.setImageUrl(dto.getImageUrl());

    if (dto.getCategory() != null) {
      Category category = categoryMapper.toEntity(dto.getCategory(), new Category());
      product.setCategory(category);
    }

    return product;
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  public ProductDto toDto(Product product, ProductDto dto) {
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setPrice(product.getPrice());
    dto.setQuantity(product.getQuantity());
    dto.setImageUrl(product.getImageUrl());

    if (product.getCategory() != null) {
      CategoryDto categoryDto = categoryMapper.toDto(new Category(), dto.getCategory());
      dto.setCategory(categoryDto);
    }

    return dto;
  }
}
