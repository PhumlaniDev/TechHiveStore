package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.dto.ProductDTO;
import com.phumlanidev.techhivestore.mapper.ProductMapper;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.model.Users;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import com.phumlanidev.techhivestore.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ProductService productService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createProductSuccessfully() {
        Category category = new Category("category", "description", new Users(), new Users(), LocalDateTime.now(), LocalDateTime.now());
        Users admin = new Users();
        ProductDTO productDTO = new ProductDTO(LocalDateTime.now(), LocalDateTime.now(), admin, admin, category, "www.image.com", 10, "100", "description","product");
        Product product = new Product();

        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(usersRepository.findByUsername("admin")).thenReturn(admin);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProductSuccessfully() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        Users admin = new Users();

        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(usersRepository.findByUsername("admin")).thenReturn(admin);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.updateProduct(productDTO);

        assertNotNull(result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void findProductByIdSuccessfully() {
        Long productId = 1L;
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.findProductById(productId);

        assertNotNull(result);
        assertEquals(productDTO, result);
    }

    @Test
    void findProductByIdNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.findProductById(productId);
        });

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void findAllProductsSuccessfully() {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        var result = productService.findAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productDTO, result.get(0));
    }

//    @Test
//    void deleteProductSuccessfully() {
//        Category category = new Category("category", "description", new Users(), new Users(), LocalDateTime.now(), LocalDateTime.now());
//        Users admin = new Users();
//        ProductDTO productDTO = new ProductDTO(LocalDateTime.now(), LocalDateTime.now(), admin, admin, category, "www.image.com", 10, "100", "description","product");
//        Product product = new Product();
//
//        when(productMapper.toEntity(productDTO)).thenReturn(product);
//        when(usersRepository.findByUsername("admin")).thenReturn(admin);
//        when(productRepository.save(any(Product.class))).thenReturn(product);
//        when(productMapper.toDTO(product)).thenReturn(productDTO);
//
//        doNothing().when(productRepository).deleteById(product.getProductId());
//
//        productService.deleteProduct(product.getProductId());
//
//        verify(productRepository, times(1)).deleteById(product.getProductId());
//    }

    @Test
    void createProductWithNullNameThrowsException() {
        ProductDTO productDTO = new ProductDTO();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(productDTO);
        });

        assertEquals("Product name cannot be null or empty", exception.getMessage());
    }

    @Test
    void createProductThatAlreadyExistsThrowsException() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("ExistingProduct");
        when(productRepository.findByName("ExistingProduct")).thenReturn(Optional.<ProductDTO>of(new Product()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.createProduct(productDTO);
        });

        assertEquals("Product already exists", exception.getMessage());
    }

    @Test
    void deleteProductNotFoundThrowsException() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(productId);
        });

        assertEquals("Product not found", exception.getMessage());
    }
}