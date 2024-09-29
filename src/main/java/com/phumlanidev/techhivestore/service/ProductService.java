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
import java.util.List;
import java.util.stream.Collectors;

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

    // create a createProduct method that will also save the product to the database and return the productDTO, also the method should be annotated with @Transactional and also should be created by an admin and should have specific categories and don't save a product with null values
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {

        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if(productRepository.findByName(productDTO.getName()).isPresent()) {
            throw new RuntimeException("Product already exists");
        }
        Product product = productMapper.toEntity(productDTO);
        Users createdBy = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        product.setCreatedBy(createdBy);
        product.setCreatedDate(LocalDateTime.now());
        product.setUpdatedDate(LocalDateTime.now());
        product.setUpdatedBy(createdBy);
        return productMapper.toDTO(productRepository.save(product));
    }
    // create a updateProduct method that will also update the product in the database and return the productDTO, also the method should be annotated with @Transactional and also should be updated by an admin
    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Users updatedBy = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        product.setUpdatedBy(updatedBy);
        product.setUpdatedDate(LocalDateTime.now());
        return productMapper.toDTO(productRepository.save(product));
    }
    // create a findProductBy method that will return an Optional<Product> and should be annotated with @Transactional
    @Transactional
    public ProductDTO findProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    // create a deleteProduct method that will delete the product from the database and should be annotated with @Transactional and should be deleted by an admin
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.deleteById(productId);
    }
    // create a findAllProducts method that will return a List<ProductDTO> and should be annotated with @Transactional
    @Transactional
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }
}
