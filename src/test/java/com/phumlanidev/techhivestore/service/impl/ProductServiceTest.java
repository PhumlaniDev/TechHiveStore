package com.phumlanidev.techhivestore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.mapper.ProductMapper;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;
  @Mock
  private ProductMapper productMapper;

  @InjectMocks
  private ProductService productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createProduct_successfulCreation() {
    ProductDto productDto = new ProductDto();
    productDto.setName("Test Product");
    Product product = new Product();

    when(productRepository.findByName("Test Product")).thenReturn(Optional.empty());
    when(productMapper.toEntity(productDto, new Product())).thenReturn(product);
    when(productRepository.save(product)).thenReturn(product);
    when(productMapper.toDto(product, productDto)).thenReturn(productDto);

    productService.createProduct(productDto);

    verify(productRepository).save(product);
    verify(productMapper).toDto(product, productDto);
  }

  @Test
  void createProduct_productAlreadyExists() {
    ProductDto productDto = new ProductDto();
    productDto.setName("Test Product");

    when(productRepository.findByName("Test Product"))
        .thenReturn(Optional.of(new Product()));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        productService.createProduct(productDto));

    assertEquals("Product already exists", exception.getMessage());
  }

  @Test
  void createProduct_productNameIsNull() {
    ProductDto productDto = new ProductDto();

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        productService.createProduct(productDto));

    assertEquals("Product name cannot be null or empty", exception.getMessage());
  }

  @Test
  void updateProduct_successfulUpdate() {
    Long productId = 1L;
    ProductDto productDto = new ProductDto();
    Product existingProduct = new Product();
    Product updatedProduct = new Product();

    when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
    when(productMapper.toEntity(productDto, existingProduct)).thenReturn(updatedProduct);
    when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

    ProductDto result = productService.updateProduct(productId, productDto);


    verify(productRepository).save(updatedProduct);
  }

  @Test
  void updateProduct_productNotFound() {
    Long productId = 1L;
    ProductDto productDto = new ProductDto();

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        productService.updateProduct(productId, productDto));

    assertEquals("Product not found", exception.getMessage());
  }

  @Test
  void findProductById_productFound() {
    Long productId = 1L;
    Product product = new Product();
    ProductDto productDto = new ProductDto();

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(productMapper.toDto(product, new ProductDto())).thenReturn(productDto);

    ProductDto result = productService.getProductById(productId);

    assertNotNull(result);
    assertEquals(productDto, result);
  }

  @Test
  void findProductById_productNotFound() {
    Long productId = 1L;

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        productService.getProductById(productId));

    assertEquals("Product not found", exception.getMessage());
  }

  @Test
  void deleteProductTest_successfulDeletion() {
    Long productId = 1L;
    Product product = new Product();

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    productService.deleteProductById(productId);

    verify(productRepository).deleteById(productId);
  }

  @Test
  void deleteProductTest_productNotFound() {
    Long productId = 1L;

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        productService.deleteProductById(productId));

    assertEquals("Product not found", exception.getMessage());
  }

  @Test
  void findAllProducts_productsFound() {
    Product product = new Product();
    ProductDto productDto = new ProductDto();

    when(productRepository.findAll()).thenReturn(List.of(product));
    when(productMapper.toDto(product, new ProductDto())).thenReturn(productDto);

    List<ProductDto> result = productService.findAllProducts();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(productDto, result.get(0));
  }

}