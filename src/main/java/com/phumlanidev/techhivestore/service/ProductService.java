package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.dto.ProductDTO;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

//    public Optional<Product> findProductBy(ProductDTO productDTO) {
//        Product product = new Product();
//        Optional<ProductDTO> existingProduct = productRepository.findByName();
//    }
}
