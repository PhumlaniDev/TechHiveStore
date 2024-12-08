package com.phumlanidev.techhivestore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.mapper.ProductMapper;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;
  @Mock
  private ProductMapper productMapper;

  @InjectMocks
  private ProductService productService;

//  @BeforeEach
//  void setUp() {
//    MockitoAnnotations.openMocks(this);
//  }

  @Test
  void createProduct_successfulCreation() {

    // Arrange
    ProductDto productDto = new ProductDto();
    productDto.setName("Test Product");

    Product product = new Product();
    when(productMapper.toEntity(eq(productDto), any(Product.class))).thenReturn(product);
    when(productRepository.findByName("Test Product")).thenReturn(Optional.empty());
    when(productRepository.save(product)).thenReturn(product);
    when(productMapper.toDto(product, productDto)).thenReturn(productDto);

    // Act
    productService.createProduct(productDto);

    // Assert
    verify(productRepository, times(1)).save(product);
  }

  @Test
  void createProduct_productAlreadyExists() {
    // Arrange
    ProductDto productDto = new ProductDto();
    productDto.setName("Existing Product");

    when(productRepository.findByName("Existing Product")).thenReturn(Optional.of(new Product()));

    // Act & Assert
    assertThrows(RuntimeException.class, () -> productService.createProduct(productDto));
  }

  @Test
  void findProductById_ShouldReturnProduct_WhenProductExists() {
    // Arrange
    Long productId = 1L;
    Product product = new Product();
    product.setProductId(productId);

    ProductDto productDto = new ProductDto();
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(productMapper.toDto(any(Product.class), any(ProductDto.class))).thenReturn(productDto);

    // Act
    ProductDto result = productService.findProductById(productId);

    // Assert
    assertNotNull(result);
    verify(productRepository, times(1)).findById(productId);
    verify(productMapper, times(1)).toDto(eq(product), any(ProductDto.class));
  }


  @Test
  void findProductById_ShouldThrowException_WhenProductDoesNotExist() {
    // Arrange
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(RuntimeException.class, () -> productService.findProductById(1L));
  }

  @Test
  void findAllProducts_ShouldReturnProductList() {
    // Arrange
    Product product = new Product();
    product.setName("Product 1");

    when(productRepository.findAll()).thenReturn(List.of(product));
    when(productMapper.toDto(eq(product), any(ProductDto.class))).thenReturn(new ProductDto());

    // Act
    List<ProductDto> products = productService.findAllProducts();

    // Assert
    assertEquals(1, products.size());
  }

  @Test
  void updateProduct_ShouldUpdateProduct_WhenValidDtoProvided() {
    // Arrange
    Long productId = 1L;
    ProductDto productDto = new ProductDto();
    productDto.setName("Updated Product");

    Product product = new Product();
    product.setProductId(productId);

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(productMapper.toEntity(any(ProductDto.class), eq(product))).thenReturn(product);
    when(productRepository.save(any(Product.class))).thenReturn(product);
    when(productMapper.toDto(any(Product.class), any(ProductDto.class))).thenReturn(productDto);

    // Act
    ProductDto result = productService.updateProduct(productId, productDto);

    // Assert
    assertNotNull(result);
    verify(productRepository, times(1)).save(product);
    verify(productMapper, times(1)).toEntity(eq(productDto), eq(product));
    verify(productMapper, times(1)).toDto(eq(product), any(ProductDto.class));
  }



  @Test
  void deleteProductById_ShouldDeleteProduct_WhenProductExists() {
    // Arrange
    Product product = new Product();
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    // Act
    productService.deleteProductById(1L);

    // Assert
    verify(productRepository, times(1)).deleteById(1L);
  }

  @Test
  void deleteProductById_ShouldThrowException_WhenProductDoesNotExist() {
    // Arrange
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(RuntimeException.class, () -> productService.deleteProductById(1L));
  }

  @Test
  void createProduct_productNameIsNull() {
    // Arrange
    ProductDto productDto = new ProductDto(); // Name is null by default

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
      () -> productService.createProduct(productDto));
    assertEquals("Product name cannot be null or empty", exception.getMessage());
  }

  @Test
  void updateProduct_productNotFound() {
    // Arrange
    Long productId = 1L;
    ProductDto productDto = new ProductDto();
    productDto.setName("Updated Product");

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class,
      () -> productService.updateProduct(productId, productDto));
    assertEquals("Product not found", exception.getMessage());
  }

}