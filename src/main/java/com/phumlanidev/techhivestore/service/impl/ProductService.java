package com.phumlanidev.techhivestore.service.impl;

import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.exception.ProductNotFoundException;
import com.phumlanidev.techhivestore.exception.product.ProductAlreadyExistsException;
import com.phumlanidev.techhivestore.mapper.ProductMapper;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import com.phumlanidev.techhivestore.utils.ProductSpecification;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
      throw new ProductAlreadyExistsException("Product already exists");
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
        .orElseThrow(() -> new ProductNotFoundException(Constant.PRODUCT_NOT_FOUND));
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Cacheable(value = "products")
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
  @Cacheable(value = "productId")
  public ProductDto getProductById(Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(Constant.PRODUCT_NOT_FOUND));
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
        .orElseThrow(() -> new ProductNotFoundException(Constant.PRODUCT_NOT_FOUND));

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
        .orElseThrow(() -> new ProductNotFoundException(Constant.PRODUCT_NOT_FOUND));
    productRepository.deleteById(productId);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Cacheable(value = "productSearchCache", key = "#name + '-' + #category + '-' + #minPrice + '-' + #maxPrice + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
  public Page<ProductDto> searchProducts(String productName, String category, BigDecimal minPrice,
                                         BigDecimal maxPrice, Pageable pageable) {
    Specification<Product> spec =
        ProductSpecification.filterProducts(productName, category, minPrice, maxPrice);

    Page<Product> productPage = productRepository.findAll(spec, pageable);

    return productPage.map(product -> productMapper.toDto(product, new ProductDto()));
  }
}
