package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.mapper.CategoryMapper;
import com.phumlanidev.techhivestore.mapper.ProductMapper;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.model.Users;
import com.phumlanidev.techhivestore.repository.CategoryRepository;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import com.phumlanidev.techhivestore.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UsersRepository usersRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

     //create a createProduct method that will also save the product to the database and return the ProductDto, also the method should be annotated with @Transactional and also should be created by an admin and should have specific categories and don't save a product with null values
    @Transactional
    public void createProduct(ProductDto productDto) {

        if (productDto.getName() == null || productDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }

        if(productRepository.findByName(productDto.getName()).isPresent()) {
            throw new RuntimeException("Product already exists");
        }

        Product product = productMapper.toEntity(productDto, new Product());
        Users createdBy = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Category category = categoryMapper.toEntity(new CategoryDto(), new Category());
        product.setCategory(category);
        product.setCreatedBy(String.valueOf(createdBy));
        Category savedCategory = categoryRepository.save(category);
        Product savedProduct = productRepository.save(product);
        categoryMapper.toEntity(new CategoryDto(), savedCategory);
        productMapper.toDto(savedProduct, productDto);
    }

    // create a updateProduct method that will also update the product in the database and return the ProductDto, also the method should be annotated with @Transactional and also should be updated by an admin
    @Transactional
    public boolean updateProduct(ProductDto productDto) {

        boolean isUpdated = false;

        Product product = productMapper.toEntity(productDto, new Product());
        product = productRepository.findById(product.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.save(product);
        isUpdated = true;

        return isUpdated;
    }

    // create a findProductBy method that will return an Optional<Product> and should be annotated with @Transactional
    @Transactional
    public ProductDto findProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDto(product, new ProductDto());
    }

    // create a deleteProduct method that will delete the product from the database and should be annotated with @Transactional and should be deleted by an admin
    @Transactional
    public boolean deleteProductTest(Long productId) {

        boolean isDeleted = false;

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.deleteById(productId);

        isDeleted = true;

        return isDeleted;
    }

    // create a findAllProducts method that will return a List<ProductDto> and should be annotated with @Transactional
    @Transactional
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> productMapper.toDto(product, new ProductDto()))
                .collect(Collectors.toList());
    }
}
