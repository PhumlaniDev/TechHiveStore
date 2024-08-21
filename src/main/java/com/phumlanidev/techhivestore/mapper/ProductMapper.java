package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.ProductDTO;
import com.phumlanidev.techhivestore.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractMapper<Product, ProductDTO> {

  @Override
  public Product toEntity(ProductDTO dto) {
    Product product = new Product();
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setQuantity(dto.getQuantity());
    product.setImageURL(dto.getImageURL());
    product.setCategory(dto.getCategory());
    return product;
  }

  @Override
  public ProductDTO toDTO(Product entity) {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setName(entity.getName());
    productDTO.setDescription(entity.getDescription());
    productDTO.setPrice(entity.getPrice());
    productDTO.setQuantity(entity.getQuantity());
    productDTO.setImageURL(entity.getImageURL());
    productDTO.setCategory(entity.getCategory());

    if (entity.getCreatedBy() != null) {
      productDTO.setCreatedByUser(entity.getCreatedBy());
    }
    if (entity.getUpdatedBy() != null) {
      productDTO.setUpdatedByUser(entity.getUpdatedBy());
    }
    productDTO.setCreatedDate(entity.getCreatedDate());
    productDTO.setUpdatedDate(entity.getUpdatedDate());

    return productDTO;
  }
}
