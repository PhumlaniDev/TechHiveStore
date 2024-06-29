package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.repository.CategoryRepository;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(Product product) {
        Category category = new Category();
        if (category == null || category.getCategoryName() == null)
            throw new IllegalArgumentException("Category and category name must not be null");
        Category savedCategory = categoryRepository.save(category);
        product.setCategory(savedCategory);
        return productRepository.save(product);
    }
}
