package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDto dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setImageURL(dto.getImageURL());
        product.setCategory(dto.getCategory());
        return product;
    }

    public ProductDto toDTO(Product product, ProductDto dto) {
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setImageURL(product.getImageURL());
        dto.setCategory(product.getCategory());
        return dto;
    }
}
