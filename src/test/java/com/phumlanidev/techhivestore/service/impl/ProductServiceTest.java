package com.phumlanidev.techhivestore.service.impl;

import com.phumlanidev.techhivestore.config.TestContainersConfig;
import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.mapper.ProductMapper;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ContextConfiguration(classes = {TestContainersConfig.class})
class ProductServiceTest {

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
    productRepository.deleteAll();

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
  }

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
  }

  @Test
  @Transactional
  void givenValidProductDto_whenCreateProduct_thenProductIsSavedSuccessfully() {
    ProductDto productDto = new ProductDto();
    productDto.setName("Test Product");
    productDto.setDescription("Test Description");
    productDto.setPrice("100.0");
    productDto.setQuantity(10);
    productDto.setImageUrl("https://test.com");

    productService.createProduct(productDto);

    Optional<Product> savedProduct = productRepository.findByName("Test Product");
    assertThat(savedProduct).isPresent();
    assertThat(savedProduct.get().getName()).isEqualTo("Test Product");
  }

  @Test
  @Transactional
  void givenProductDtoWithExistingName_whenCreateProduct_thenThrowsException() {
    Product product = new Product();
    product.setName("Existing Product");
    product.setDescription("Description");
    product.setPrice("100.0");
    product.setQuantity(10);
    product.setImageUrl("https://test.com");
    productRepository.save(product);

    ProductDto productDto = new ProductDto();
    productDto.setName("Existing Product");

    assertThatThrownBy(() -> productService.createProduct(productDto)).isInstanceOf(
      RuntimeException.class).hasMessage("Product already exists");
  }

  @Test
  @Transactional
  void givenProductDtoWithNullName_whenCreateProduct_thenThrowsException() {
    ProductDto productDto = new ProductDto();
    productDto.setName(null);

    assertThatThrownBy(() -> productService.createProduct(productDto)).isInstanceOf(
      IllegalArgumentException.class).hasMessage("Product name cannot be null or empty");
  }

  @Test
  @Transactional
  void givenExistingProductId_whenFindProductById_thenReturnProductDto() {
    productRepository.save(product);

    ProductDto foundProduct = productService.findProductById(1L);

    assertThat(foundProduct).isNotNull();
    assertThat(foundProduct.getName()).isEqualTo("Test Product");
    assertThat(foundProduct.getDescription()).isEqualTo("Test Description");
  }

  @Test
  @Transactional
  void givenNonExistingProductId_whenFindProductById_thenThrowRuntimeException() {
    productRepository.deleteAll();

    assertThrows(RuntimeException.class, () -> productService.findProductById(2L));
  }

  @Test
  @Transactional
  void givenValidProductDto_whenUpdateProduct_thenProductIsUpdatedSuccessfully() {
    Product savedProduct = productRepository.save(product);
    Long productId = savedProduct.getProductId();

    ProductDto result = productService.updateProduct(productId, productDto);

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
  void givenNonExistingProductId_whenUpdateProduct_thenThrowRuntimeException() {
    ProductDto updatedProductDto = new ProductDto();
    updatedProductDto.setName("Updated Product");

    assertThatThrownBy(() -> productService.updateProduct(2L, updatedProductDto)).isInstanceOf(
      RuntimeException.class).hasMessage("Product not found");
  }

  @Test
  @Transactional
  void givenExistingProductId_whenDeleteProductById_thenProductIsDeletedSuccessfully() {
    Product savedProduct = productRepository.save(product);
    Long productId = savedProduct.getProductId();

    productService.deleteProductById(productId);

    Optional<Product> deletedProduct = productRepository.findById(productId);
    assertThat(deletedProduct).isNotPresent();
  }

  @Test
  @Transactional
  void givenNonExistingProductId_whenDeleteProductById_thenThrowRuntimeException() {
    assertThatThrownBy(() -> productService.deleteProductById(2L)).isInstanceOf(
      RuntimeException.class).hasMessage("Product not found");
  }

  @Test
  @Transactional
  void givenValidProductDto_whenUpdateProduct_thenProductIsUpdated() {
    Product savedProduct = productRepository.save(product);
    Long productId = savedProduct.getProductId();

    ProductDto result = productService.updateProduct(productId, productDto);

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
    Long nonExistingProductId = 999L;

    assertThatThrownBy(
      () -> productService.updateProduct(nonExistingProductId, productDto)).isInstanceOf(
      RuntimeException.class).hasMessage("Product not found");
  }

  @Test
  @Transactional
  void givenExistingProductId_whenGetProductById_thenReturnProductDto() {
    Product savedProduct = productRepository.save(product);
    Long productId = savedProduct.getProductId();

    ProductDto result = productService.getProductById(productId);

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
    Long nonExistingProductId = 999L;

    assertThatThrownBy(() -> productService.getProductById(nonExistingProductId)).isInstanceOf(
      RuntimeException.class).hasMessage("Product not found");
  }

  @Test
  @Transactional
  void whenFindAllProducts_thenReturnProductDtoList() {
    productRepository.save(product);

    List<ProductDto> result = productService.findAllProducts();

    assertThat(result).isNotEmpty();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("Test Product");
    assertThat(result.get(0).getDescription()).isEqualTo("Test Description");
    assertThat(result.get(0).getPrice()).isEqualTo("100.0");
    assertThat(result.get(0).getQuantity()).isEqualTo(10);
    assertThat(result.get(0).getImageUrl()).isEqualTo("https://test.com");
  }
}