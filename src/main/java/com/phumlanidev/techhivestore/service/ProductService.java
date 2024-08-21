package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.dto.ProductDTO;
import com.phumlanidev.techhivestore.mapper.ProductMapper;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.model.Users;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import com.phumlanidev.techhivestore.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final ProductMapper productMapper;

    public ProductService(
            ProductRepository productRepository,
            UsersRepository usersRepository,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.usersRepository = usersRepository;
      this.productMapper = productMapper;
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDto) {
        Product product = productMapper.toEntity(productDto);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users admin = usersRepository.findByUsername(username);

        product.setCreatedBy(admin);
        product.setCreatedDate(LocalDateTime.now());

        productRepository.save(product);

        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productMapper.toEntity(productDTO);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users admin = usersRepository.findByUsername(username);

        product.setUpdatedBy(admin);
        product.setUpdatedDate(LocalDateTime.now());

        productRepository.save(product);

        // Additional logic...
        return productMapper.toDTO(product);
    }

//    public Optional<Product> findProductBy(ProductDTO productDTO) {
//        Product product = new Product();
//        Optional<ProductDTO> existingProduct = productRepository.findByName();
//    }
}
