package com.phumlanidev.techhivestore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.phumlanidev.techhivestore.dto.OrderDto;
import com.phumlanidev.techhivestore.enums.OrderStatus;
import com.phumlanidev.techhivestore.mapper.OrderMapper;
import com.phumlanidev.techhivestore.model.Address;
import com.phumlanidev.techhivestore.model.Cart;
import com.phumlanidev.techhivestore.model.CartItem;
import com.phumlanidev.techhivestore.model.Order;
import com.phumlanidev.techhivestore.model.OrderItem;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.model.User;
import com.phumlanidev.techhivestore.repository.AddressRepository;
import com.phumlanidev.techhivestore.repository.CartRepository;
import com.phumlanidev.techhivestore.repository.OrderRepository;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import com.phumlanidev.techhivestore.repository.UserRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrdersServiceImplTest {

  @Mock
  private CartRepository cartRepository;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private AddressRepository addressRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private OrderMapper orderMapper;

  @InjectMocks
  private OrdersServiceImpl ordersService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testPlaceOrder_Success() {
    // Arrange
    Long userId = 1L;
    User mockUser = new User();
    mockUser.setUserId(userId);

    Long addressId = 2L;
    Address mockAddress = new Address();
    mockAddress.setAddressId(addressId);

    Cart mockCart = new Cart();
    mockCart.setTotalPrice(100.0);
    mockCart.setCartItems(new ArrayList<>());

    CartItem cartItem = new CartItem();
    cartItem.setQuantity(2);
    Product mockProduct = new Product();
    mockProduct.setQuantity(10);
    mockProduct.setPrice("20.0");
    cartItem.setProduct(mockProduct);

    mockCart.getCartItems().add(cartItem);

    Order mockOrder = new Order();
    OrderDto mockOrderDto = new OrderDto();

    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
    when(addressRepository.findById(addressId)).thenReturn(Optional.of(mockAddress));
    when(cartRepository.findByUser_UserId(userId)).thenReturn(Optional.of(mockCart));
    when(orderMapper.toDto(any(Order.class), any(OrderDto.class))).thenReturn(mockOrderDto);

    // Act
    String paymentMethod = "CREDIT_CARD";
    OrderDto result = ordersService.placeOrder(userId, addressId);

    // Assert
    assertNotNull(result);
    verify(productRepository, times(1)).save(mockProduct); // Stock was updated
    verify(orderRepository, times(1)).save(any(Order.class)); // Order was saved
    verify(cartRepository, times(1)).delete(mockCart); // Cart was cleared
  }

  @Test
  void testPlaceOrder_UserNotFound() {
    // Arrange
    Long userId = 1L; // Test-specific setup
    Long addressId = 2L;

    when(userRepository.findById(userId)).thenReturn(Optional.empty()); // Only required mock

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class,
      () -> ordersService.placeOrder(userId, addressId));

    assertEquals("user not found", exception.getMessage());
  }


  @Test
  void testCancelOrder_Success() {
    // Arrange
    Long orderId = 1L;

    Order mockOrder = new Order();
    mockOrder.setOrderStatus(OrderStatus.PENDING);
    mockOrder.setItems(new ArrayList<>());

    OrderItem mockItem = new OrderItem();
    Product mockProduct = new Product();
    mockProduct.setQuantity(5);
    mockItem.setProductId(mockProduct);
    mockItem.setQuantity(3);
    mockOrder.getItems().add(mockItem);

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

    // Act
    ordersService.cancelOrder(orderId);

    // Assert
    verify(productRepository, times(1)).save(mockProduct); // Stock was refunded
    verify(orderRepository, times(1)).save(mockOrder); // Order status was updated
    assertEquals(OrderStatus.CANCELLED, mockOrder.getOrderStatus());
  }
}