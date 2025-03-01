package com.phumlanidev.techhivestore.service.impl;

import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.mapper.ProductMapper;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(SpringExtension.class)
@Testcontainers
@SpringBootTest // Enables testing with JPA
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// Uses Testcontainers DB instead of H2
@Import(ProductService.class)
class ProductServiceTest {

  @Container
  private static final PostgreSQLContainer<?> postgres =
    new PostgreSQLContainer<>("postgres:16").withDatabaseName("test_db").withUsername("test")
      .withPassword("test");

  static {
    postgres.start();  // Manually start Testcontainers PostgreSQL
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", () -> "jdbc:tc:postgresql:16:///test_db");
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductMapper productMapper;

  private Product product;

  private ProductDto productDto;

  @BeforeEach
  void setUp() {
    product = new Product();
    product.setProductId(1L);
    product.setName("Test Product");
    product.setDescription("Test Description");
    product.setPrice("100.0");
    product.setQuantity(10);
    product.setImageUrl("https://test.com");

    productDto = new ProductDto();
    productDto.setName("Test Product");
    productDto.setDescription("Test Description");
    productDto.setPrice("100.0");
    productDto.setQuantity(10);
    productDto.setImageUrl("https://test.com");

    productRepository.deleteAll(); // Clean up before each test
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void givenValidProductDto_whenCreateProduct_thenProductIsSavedSuccessfully() {
    // Given
    ProductDto productDto = new ProductDto();
    productDto.setName("Test Product");
    productDto.setDescription("Test Description");
    productDto.setPrice("100.0");
    productDto.setQuantity(10);
    productDto.setImageUrl("https://test.com");

    // When
    productService.createProduct(productDto);

    // Then
    Optional<Product> savedProduct = productRepository.findByName("Test Product");
    assertThat(savedProduct).isPresent();
    assertThat(savedProduct.get().getName()).isEqualTo("Test Product");
  }

  @Test
  void givenProductDtoWithExistingName_whenCreateProduct_thenThrowsException() {
    // Given
    Product product = new Product();
    product.setName("Existing Product");
    product.setDescription("Description");
    product.setPrice("100.0");
    product.setQuantity(10);
    product.setImageUrl("https://test.com");
    productRepository.save(product);  // Pre-save a product

    ProductDto productDto = new ProductDto();
    productDto.setName("Existing Product");

    // When & Then
    assertThatThrownBy(() -> productService.createProduct(productDto)).isInstanceOf(
      RuntimeException.class).hasMessage("Product already exists");
  }

  @Test
  void givenProductDtoWithNullName_whenCreateProduct_thenThrowsException() {
    // Given
    ProductDto productDto = new ProductDto();
    productDto.setName(null);

    // When & Then
    assertThatThrownBy(() -> productService.createProduct(productDto)).isInstanceOf(
      IllegalArgumentException.class).hasMessage("Product name cannot be null or empty");
  }

  @Test
  void givenExistingProductId_whenFindProductById_thenReturnProductDto() {
    // Arrange
    productRepository.save(product); // Ensure the product is saved in the repository

    // Act
    ProductDto foundProduct = productService.findProductById(1L);

    // Assert
    assertThat(foundProduct).isNotNull();
    assertThat(foundProduct.getName()).isEqualTo("Test Product");
    assertThat(foundProduct.getDescription()).isEqualTo("Test Description");
  }

  @Test
  void givenNonExistingProductId_whenFindProductById_thenThrowRuntimeException() {
    // Given: Ensure the database is empty (or ID `2L` does not exist)
    productRepository.deleteAll();

    // Act & Assert
    assertThrows(RuntimeException.class, () -> productService.findProductById(2L));
  }

  @Test
  void givenValidProductDto_whenUpdateProduct_thenProductIsUpdatedSuccessfully() {

    // Arrange
    Product savedProduct = productRepository.save(product);
    Long productId = savedProduct.getProductId();


    // When
    ProductDto result = productService.updateProduct(productId, productDto);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("Test Product");
    assertThat(result.getDescription()).isEqualTo("Test Description");
    assertThat(result.getPrice()).isEqualTo("100.0");
    assertThat(result.getQuantity()).isEqualTo(10);
    assertThat(result.getImageUrl()).isEqualTo("https://test.com");

    Optional<Product> updatedProduct = productRepository.findById(productId);
    assertThat(updatedProduct).isPresent();
    assertThat(updatedProduct.get().getName()).isEqualTo("Test Product");
    assertThat(updatedProduct.get().getDescription()).isEqualTo("Test Description");
    assertThat(updatedProduct.get().getPrice()).isEqualTo("100.0");
    assertThat(updatedProduct.get().getQuantity()).isEqualTo(10);
    assertThat(updatedProduct.get().getImageUrl()).isEqualTo("https://test.com");
  }

  @Test
  void givenNonExistingProductId_whenUpdateProduct_thenThrowRuntimeException() {
    // Given
    ProductDto updatedProductDto = new ProductDto();
    updatedProductDto.setName("Updated Product");

    // When & Then
    assertThatThrownBy(() -> productService.updateProduct(2L, updatedProductDto)).isInstanceOf(
      RuntimeException.class).hasMessage("Product not found");
  }

  @Test
  void givenExistingProductId_whenDeleteProductById_thenProductIsDeletedSuccessfully() {
    // Given
    Product savedProduct = productRepository.save(product);
    Long productId = savedProduct.getProductId(); // Get generated ID

    // When
    productService.deleteProductById(productId);

    // Then
    Optional<Product> deletedProduct = productRepository.findById(productId);
    assertThat(deletedProduct).isNotPresent();
  }

  @Test
  void givenNonExistingProductId_whenDeleteProductById_thenThrowRuntimeException() {
    // When & Then
    assertThatThrownBy(() -> productService.deleteProductById(2L)).isInstanceOf(
      RuntimeException.class).hasMessage("Product not found");
  }

  @Test
  @Transactional
  void givenValidProductDto_whenUpdateProduct_thenProductIsUpdated() {
    // Arrange
    Product savedProduct = productRepository.save(product);
    Long productId = savedProduct.getProductId();

    // Act
    ProductDto result = productService.updateProduct(productId, productDto);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("Test Product");
    assertThat(result.getDescription()).isEqualTo("Test Description");
    assertThat(result.getPrice()).isEqualTo("100.0");
    assertThat(result.getQuantity()).isEqualTo(10);
    assertThat(result.getImageUrl()).isEqualTo("https://test.com");

    Optional<Product> updatedProduct = productRepository.findById(productId);
    assertThat(updatedProduct).isPresent();
    assertThat(updatedProduct.get().getName()).isEqualTo("Test Product");
    assertThat(updatedProduct.get().getDescription()).isEqualTo("Test Description");
    assertThat(updatedProduct.get().getPrice()).isEqualTo("100.0");
    assertThat(updatedProduct.get().getQuantity()).isEqualTo(10);
    assertThat(updatedProduct.get().getImageUrl()).isEqualTo("https://test.com");
  }

  @Test
  @Transactional
  void givenNonExistingProductId_whenUpdateProduct_thenThrowException() {
    // Given
    Long nonExistingProductId = 999L;

    // When & Then
    assertThatThrownBy(
      () -> productService.updateProduct(nonExistingProductId, productDto)).isInstanceOf(
      RuntimeException.class).hasMessage("Product not found");
  }

  @Test
  @Transactional
  void givenExistingProductId_whenGetProductById_thenReturnProductDto() {
    // Arrange
    Product savedProduct = productRepository.save(product);
    Long productId = savedProduct.getProductId();

    // Act
    ProductDto result = productService.getProductById(productId);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("Test Product");
    assertThat(result.getDescription()).isEqualTo("Test Description");
    assertThat(result.getPrice()).isEqualTo("100.0");
    assertThat(result.getQuantity()).isEqualTo(10);
    assertThat(result.getImageUrl()).isEqualTo("https://test.com");
  }

  @Test
  @Transactional
  void givenNonExistingProductId_whenGetProductById_thenThrowRuntimeException() {
    // Given
    Long nonExistingProductId = 999L;

    // When & Then
    assertThatThrownBy(() -> productService.getProductById(nonExistingProductId)).isInstanceOf(
      RuntimeException.class).hasMessage("Product not found");
  }

  @Test
  @Transactional
  void whenFindAllProducts_thenReturnProductDtoList() {
    // Arrange
    productRepository.save(product);

    // Act
    List<ProductDto> result = productService.findAllProducts();

    // Assert
    assertThat(result).isNotEmpty();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("Test Product");
    assertThat(result.get(0).getDescription()).isEqualTo("Test Description");
    assertThat(result.get(0).getPrice()).isEqualTo("100.0");
    assertThat(result.get(0).getQuantity()).isEqualTo(10);
    assertThat(result.get(0).getImageUrl()).isEqualTo("https://test.com");
  }
}