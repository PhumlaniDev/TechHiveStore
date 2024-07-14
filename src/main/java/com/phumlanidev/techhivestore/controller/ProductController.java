package com.phumlanidev.techhivestore.controller;

import com.phumlanidev.techhivestore.dto.ProductDTO;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = mapProductDTOToEntity(productDTO);
        return new  ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    private Product mapProductDTOToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setImageURL(productDTO.getImageURL());

        // Map CategoryDTO to Category entity
        Category category = new Category();
        category.setCategoryName(productDTO.getCategory().getCategoryName());
        category.setDescription(productDTO.getCategory().getDescription());
        product.setCategory(category);

        return product;
    }
}
