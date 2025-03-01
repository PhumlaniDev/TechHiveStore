package com.phumlanidev.techhivestore.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.phumlanidev.techhivestore.model.Address;
import com.phumlanidev.techhivestore.model.Cart;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.model.User;
import com.phumlanidev.techhivestore.repository.AddressRepository;
import com.phumlanidev.techhivestore.repository.CartRepository;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import com.phumlanidev.techhivestore.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class CartServiceTest {

  @Container
  private static final PostgreSQLContainer<?> postgresContainer =
    new PostgreSQLContainer<>("postgres:16")
    .withDatabaseName("test_db")
    .withUsername("test")
    .withPassword("test");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @Mock
  private CartRepository cartRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private AddressRepository addressRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private CartService cartService;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeAll
  static void startContainer() {
    postgresContainer.start();
    System.out.println("PostgreSQL running on: " + postgresContainer.getJdbcUrl());
  }



  @AfterAll
  static void stopContainer() {
    postgresContainer.stop();
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Transactional
  @Test
  void addItemToCart_createsNewCartIfNotExists() {
    Long userId = 1L;
    Long productId = 1L;
    int quantity = 2;

    when(cartRepository.findByUser_UserId(userId)).thenReturn(Optional.empty());
    when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
    when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));

    cartService.addItemToCart(userId, productId, quantity);

    verify(cartRepository).save(any(Cart.class));
  }

  @Transactional
  @Test
  void testAddressDataInitialization() {
    List<Address> addresses = addressRepository.findAll();
    assertThat(addresses).hasSize(3);
  }

  @Transactional
  @Test
  void removeItemFromCart() {
  }

  @Transactional
  @Test
  void clearCart() {
  }

  @Transactional
  @Test
  void getCartDetails() {
  }

  @Test
  @Rollback(false)
  void verifyDataLoadedFromSql() {
    // Verify Users table
    Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    assertThat(userCount).isEqualTo(3);

    User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = 1", (rs, rowNum) -> {
      User u = new User();
      u.setUserId(rs.getLong("user_id"));
      u.setUsername(rs.getString("username"));
      u.setEmail(rs.getString("email"));
      return u;
    });
    assertThat(user).isNotNull();
    assertThat(user.getUsername()).isEqualTo("johndoe");
    assertThat(user.getEmail()).isEqualTo("johndoe@example.com");

    // Verify Product table
    Integer productCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM product", Integer.class);
    assertThat(productCount).isEqualTo(4);

    // Add more assertions as needed for other tables
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    public JdbcTemplate jdbcTemplate() {
      return new JdbcTemplate(new DriverManagerDataSource(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword()));
    }
  }
}