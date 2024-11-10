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
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Comment: this is the placeholder for documentation.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final UsersRepository usersRepository;
  private final ProductMapper productMapper;
  private final CategoryMapper categoryMapper;


  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public void createProduct(ProductDto productDto) {

    if (productDto.getName() == null || productDto.getName().isEmpty()) {
      throw new IllegalArgumentException("Product name cannot be null or empty");
    }

    if (productRepository.findByName(productDto.getName()).isPresent()) {
      throw new RuntimeException("Product already exists");
    }

    Product product = productMapper.toEntity(productDto, new Product());

    Users createdBy = usersRepository.findByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName());

    CategoryDto categoryDto = productDto.getCategory();
    if (categoryDto == null) {
      throw new IllegalArgumentException("Category cannot be null");
    }

    Category category = categoryMapper.toEntity(categoryDto, new Category());
    product.setCategory(category);

    Product savedProduct = productRepository.save(product);

    productMapper.toDto(savedProduct, productDto);
  }


  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public boolean updateProduct(Long productId, ProductDto productDto) {

    boolean isUpdated = false;

    Product existingProduct = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    Product updatedProduct = productMapper.toEntity(productDto, existingProduct);

    Category category = categoryRepository.findByCategoryName(
            productDto.getCategory().getCategoryName())
            .orElseThrow(() -> new RuntimeException("Category not found"));
    existingProduct.setCategory(category);

    productRepository.save(updatedProduct);
    isUpdated = true;

    return isUpdated;
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public ProductDto findProductById(Long productId) {
    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    return productMapper.toDto(product, new ProductDto());
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public boolean deleteProductTest(Long productId) {

    boolean isDeleted = false;

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    productRepository.deleteById(productId);

    isDeleted = true;

    return isDeleted;
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public List<ProductDto> findAllProducts() {
    return productRepository.findAll()
            .stream()
            .map(product -> productMapper.toDto(product, new ProductDto()))
            .collect(Collectors.toList());
  }
}
