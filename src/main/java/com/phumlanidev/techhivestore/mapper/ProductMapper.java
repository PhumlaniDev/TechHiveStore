package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

  private final CategoryMapper categoryMapper;

  public Product toEntity(ProductDto dto, Product product) {
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setQuantity(dto.getQuantity());
    product.setImageURL(dto.getImageURL());

    if (dto.getCategory() != null) {
      Category category = categoryMapper.toEntity(dto.getCategory(), new Category());
      product.setCategory(category);
    }

    return product;
  }

  public ProductDto toDto(Product product, ProductDto dto) {
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setPrice(product.getPrice());
    dto.setQuantity(product.getQuantity());
    dto.setImageURL(product.getImageURL());

    if (product.getCategory() != null) {
      CategoryDto categoryDto = categoryMapper.toDTO(new Category(), dto.getCategory());
      dto.setCategory(categoryDto);
    }

    return dto;
  }
}
