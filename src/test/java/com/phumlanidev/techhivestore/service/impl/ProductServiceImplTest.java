package com.phumlanidev.techhivestore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.exception.ProductNotFoundException;
import com.phumlanidev.techhivestore.exception.product.ProductAlreadyExistsException;
import com.phumlanidev.techhivestore.mapper.ProductMapper;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl productServiceImpl;

  @Mock
  private ProductRepository productRepository;
  @Mock
  private ProductMapper productMapper;

  private Product product;
  private ProductDto productDto;

  @BeforeEach
  void setup() {
    try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {

      product = new Product();
      product.setProductId(1L);
      product.setName("Laptop");
      product.setPrice(1000);

      productDto = new ProductDto();
      productDto.setName("Laptop");

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void createProduct_success() {
    when(productRepository.findByName(productDto.getName())).thenReturn(Optional.empty());
    when(productRepository.save(any(Product.class))).thenReturn(product);
    when(productMapper.toEntity(eq(productDto), any(Product.class))).thenReturn(product);

    productServiceImpl.createProduct(productDto);

    verify(productRepository).save(product);
  }

  @Test
  void createProduct_shouldThrowAlreadyExists() {
    when(productRepository.findByName(productDto.getName())).thenReturn(Optional.of(product));

    assertThrows(ProductAlreadyExistsException.class,
        () -> productServiceImpl.createProduct(productDto));
  }

  @Test
  void findProductById_success() {
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    when(productMapper.toDto(eq(product), any(ProductDto.class))).thenReturn(new ProductDto());

    ProductDto result = productServiceImpl.findProductById(1L);

    assertNotNull(result);
  }

  @Test
  void findProductById_shouldThrowNotFound() {
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> productServiceImpl.findProductById(1L));
  }

  @Test
  void findAllProducts_success() {
    when(productRepository.findAll()).thenReturn(List.of(product));
    when(productMapper.toDto(eq(product), any(ProductDto.class))).thenReturn(new ProductDto());

    List<ProductDto> result = productServiceImpl.findAllProducts();

    assertEquals(1, result.size());
  }

  @Test
  void updateProduct_success() {
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    when(productRepository.save(any(Product.class))).thenReturn(product);
    when(productMapper.toEntity(eq(productDto), eq(product))).thenReturn(product);
    when(productMapper.toDto(eq(product), any(ProductDto.class))).thenReturn(new ProductDto());

    ProductDto result = productServiceImpl.updateProduct(1L, productDto);

    assertNotNull(result);
    verify(productRepository).save(product);
  }

  @Test
  void deleteProductById_success() {
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    productServiceImpl.deleteProductById(1L);

    verify(productRepository).deleteById(1L);
  }

  @Test
  void deleteProductById_shouldThrowNotFound() {
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> productServiceImpl.deleteProductById(1L));
  }

  @Test
  void searchProducts_success() {
    Page<Product> productPage = new PageImpl<>(List.of(product));
    Pageable pageable = PageRequest.of(0, 10);
    when(productRepository.findAll(ArgumentMatchers.<Specification<Product>>any(),
        eq(pageable))).thenReturn(productPage);
    when(productMapper.toDto(eq(product), any(ProductDto.class))).thenReturn(new ProductDto());

    Page<ProductDto> result =
        productServiceImpl.searchProducts("Laptop", "Electronics", BigDecimal.ZERO, BigDecimal.TEN,
            pageable);

    assertEquals(1, result.getTotalElements());
  }
}