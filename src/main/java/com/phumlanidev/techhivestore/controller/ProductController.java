package com.phumlanidev.techhivestore.controller;

import com.phumlanidev.techhivestore.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @PostMapping
//    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDTO) {
//        return new  ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
//    }

//    private Product mapProductDTOToEntity(ProductDTO productDTO) {
//        Product product = new Product();
//        product.setName(productDTO.getName());
//        product.setDescription(productDTO.getDescription());
//        product.setPrice(productDTO.getPrice());
//        product.setQuantity(productDTO.getQuantity());
//        product.setImageURL(productDTO.getImageURL());
//
//        // Map CategoryDTO to Category entity
//        Category category = new Category();
//        category.setCategoryName(productDTO.getCategory().getCategoryName());
//        category.setDescription(productDTO.getCategory().getDescription());
//        product.setCategory(category);
//
//        return product;
//    }
}
