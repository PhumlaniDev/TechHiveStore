package com.phumlanidev.techhivestore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import com.phumlanidev.techhivestore.repository.UserRepository;
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
  private OrderMapper orderMapper;

  @InjectMocks
  private OrdersServiceImpl ordersService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void placeOrder_successfulOrderPlacement() {
    User user = new User();
    user.setUserId(1L);
    Address address = new Address();
    address.setAddressId(1L);
    Cart cart = new Cart();
    cart.setTotalPrice(100.0);
    CartItem cartItem = new CartItem();
    Product product = new Product();
    product.setQuantity(10);
    product.setPrice("10.0");
    cartItem.setProduct(product);
    cartItem.setQuantity(5);
    cart.setCartItems(List.of(cartItem));
    OrderDto orderDto = new OrderDto();

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
    when(cartRepository.findByUser_UserId(1L)).thenReturn(Optional.of(cart));
    when(orderMapper.toDto(any(Order.class), any(OrderDto.class))).thenReturn(orderDto);

    OrderDto result = ordersService.placeOrder(1L, 1L, "credit_card");

    assertNotNull(result);
    verify(orderRepository).save(any(Order.class));
    verify(cartRepository).delete(cart);
  }

  @Test
  void placeOrder_userNotFound() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        ordersService.placeOrder(1L, 1L, "credit_card"));

    assertEquals("user not found", exception.getMessage());
  }

  @Test
  void placeOrder_addressNotFound() {
    User user = new User();
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(addressRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        ordersService.placeOrder(1L, 1L, "credit_card"));

    assertEquals("Address not found", exception.getMessage());
  }

  @Test
  void placeOrder_cartNotFound() {
    User user = new User();
    Address address = new Address();
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
    when(cartRepository.findByUser_UserId(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        ordersService.placeOrder(1L, 1L, "credit_card"));

    assertEquals("Cart not found", exception.getMessage());
  }

  @Test
  void placeOrder_insufficientStock() {

    Cart cart = new Cart();
    cart.setTotalPrice(100.0);
    CartItem cartItem = new CartItem();
    Product product = new Product();
    product.setQuantity(2);
    product.setPrice("10.0");
    cartItem.setProduct(product);
    cartItem.setQuantity(5);
    cart.setCartItems(List.of(cartItem));
    Address address = new Address();
    User user = new User();

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
    when(cartRepository.findByUser_UserId(1L)).thenReturn(Optional.of(cart));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        ordersService.placeOrder(1L, 1L, "credit_card"));

    assertEquals("Insufficient stock for product: null", exception.getMessage());
  }

  @Test
  void getOrderDetails_orderFound() {
    Order order = new Order();
    OrderDto orderDto = new OrderDto();

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(orderMapper.toDto(order, new OrderDto())).thenReturn(orderDto);

    OrderDto result = ordersService.getOrderDetails(1L);

    assertNotNull(result);
    assertEquals(orderDto, result);
  }

  @Test
  void getOrderDetails_orderNotFound() {
    when(orderRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        ordersService.getOrderDetails(1L));

    assertEquals("Order not found", exception.getMessage());
  }

  @Test
  void getUserOrders_ordersFound() {
    Order order = new Order();
    OrderDto orderDto = new OrderDto();

    when(orderRepository.findByUserId_UserId(1L)).thenReturn(List.of(order));
    when(orderMapper.toDto(order, new OrderDto())).thenReturn(orderDto);

    List<OrderDto> result = ordersService.getUserOrders(1L);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(orderDto, result.get(0));
  }

  @Test
  void updateOrderStatus_orderFound() {
    Order order = new Order();
    order.setOrderStatus(OrderStatus.PENDING);
    OrderDto orderDto = new OrderDto();

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(orderMapper.toDto(order, new OrderDto())).thenReturn(orderDto);

    OrderDto result = ordersService.updateOrderStatus(1L, "SHIPPED");

    assertNotNull(result);
    assertEquals(OrderStatus.SHIPPED, order.getOrderStatus());
  }

  @Test
  void updateOrderStatus_orderNotFound() {
    when(orderRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        ordersService.updateOrderStatus(1L, "SHIPPED"));

    assertEquals("Order not found", exception.getMessage());
  }

  @Test
  void cancelOrder_successfulCancellation() {
    Order order = new Order();
    order.setOrderStatus(OrderStatus.PENDING);
    OrderItem orderItem = new OrderItem();
    Product product = new Product();
    product.setQuantity(10);
    orderItem.setProductId(product);
    orderItem.setQuantity(5);
    order.setItems(List.of(orderItem));

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

    ordersService.cancelOrder(1L);

    assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());
    assertEquals(15, product.getQuantity());
    verify(orderRepository).save(order);
  }

  @Test
  void cancelOrder_orderNotFound() {
    when(orderRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        ordersService.cancelOrder(1L));

    assertEquals("Order not found", exception.getMessage());
  }

  @Test
  void cancelOrder_nonPendingOrder() {
    Order order = new Order();
    order.setOrderStatus(OrderStatus.SHIPPED);

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        ordersService.cancelOrder(1L));

    assertEquals("Only pending orders can be canceled", exception.getMessage());
  }
}