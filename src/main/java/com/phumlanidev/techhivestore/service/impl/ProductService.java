package com.phumlanidev.techhivestore.service.impl;

import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.mapper.ProductMapper;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Comment: this is the placeholder for documentation.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;


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

    Product savedProduct = productRepository.save(product);

    productMapper.toDto(savedProduct, productDto);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public ProductDto findProductById(Long productId) {
    return productRepository.findById(productId)
        .map(product -> productMapper.toDto(product, new ProductDto()))
        .orElseThrow(() -> new RuntimeException(Constant.PRODUCT_NOT_FOUND));
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public List<ProductDto> findAllProducts() {

    List<Product> products = productRepository.findAll();

    return products.stream()
        .filter(product -> product.getName() != null && !product.getName().isEmpty())
        .map(product -> productMapper.toDto(product, new ProductDto())).toList();
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public ProductDto getProductById(Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException(Constant.PRODUCT_NOT_FOUND));
    return productMapper.toDto(product, new ProductDto());
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public ProductDto updateProduct(Long productId, ProductDto productDto) {
    if (productDto.getName() == null || productDto.getName().isEmpty()) {
      throw new IllegalArgumentException("product name cannot be null or empty");
    }

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException(Constant.PRODUCT_NOT_FOUND));

    productMapper.toEntity(productDto, product);

    Product updateProduct = productRepository.save(product);

    return productMapper.toDto(updateProduct, new ProductDto());
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Transactional
  public void deleteProductById(Long productId) {

    productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException(Constant.PRODUCT_NOT_FOUND));
    productRepository.deleteById(productId);
  }
}
