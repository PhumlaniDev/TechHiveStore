package com.phumlanidev.techhivestore.service.impl;

import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.dto.OrderDto;
import com.phumlanidev.techhivestore.enums.OrderStatus;
import com.phumlanidev.techhivestore.enums.PaymentStatus;
import com.phumlanidev.techhivestore.exception.UserNotFoundException;
import com.phumlanidev.techhivestore.exception.cart.CartEmptyException;
import com.phumlanidev.techhivestore.exception.order.OrderAlreadyProcessedException;
import com.phumlanidev.techhivestore.exception.order.OrderNotFoundException;
import com.phumlanidev.techhivestore.mapper.OrderMapper;
import com.phumlanidev.techhivestore.model.Address;
import com.phumlanidev.techhivestore.model.Cart;
import com.phumlanidev.techhivestore.model.Order;
import com.phumlanidev.techhivestore.model.User;
import com.phumlanidev.techhivestore.repository.AddressRepository;
import com.phumlanidev.techhivestore.repository.CartRepository;
import com.phumlanidev.techhivestore.repository.OrderRepository;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import com.phumlanidev.techhivestore.repository.UserRepository;
import com.phumlanidev.techhivestore.service.IOrdersService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Comment: this is the placeholder for documentation.
 */
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements IOrdersService {

  private final CartRepository cartRepository;
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final AddressRepository addressRepository;
  private final ProductRepository productRepository;
  private final OrderMapper orderMapper;

  @Transactional
  @Override
  public OrderDto placeOrder(Long userId, Long addressId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("user not found"));

    // Fetch cart
    Cart cart = cartRepository.findByUserUserId(user.getUserId())
        .orElseThrow(() -> new RuntimeException("Cart not found"));

    if (cart.getTotalPrice() <= 0) {
      throw new CartEmptyException("Cart is empty");
    }

    // Create order entity
    Order order = new Order();
    order.setUserId(user);
    order.setOrderNumber(UUID.randomUUID());
    order.setOrderStatus(OrderStatus.PENDING);
    order.setPaymentStatus(PaymentStatus.PENDING);
    order.setTotalPrice(cart.getTotalPrice());

    Address address = addressRepository.findById(addressId)
        .orElseThrow(() -> new RuntimeException("Address not found"));

    order.setAddressId(address);

    //Save order
    orderRepository.save(order);

    // Clear cart
    cartRepository.delete(cart);

    return orderMapper.toDto(order, new OrderDto());

  }

  @Transactional
  @Override
  public OrderDto getOrderDetails(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(Constant.ORDER_NOT_FOUND));
    return orderMapper.toDto(order, new OrderDto());
  }

  @Transactional
  @Override
  public List<OrderDto> getUserOrders(Long userId) {
    List<Order> orders = orderRepository.findByUserId_UserId(userId);
    return orders.stream().map(order -> orderMapper.toDto(order, new OrderDto())).toList();
  }

  @Transactional
  @Override
  public OrderDto updateOrderStatus(Long orderId, String status) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));

    order.setOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
    return orderMapper.toDto(orderRepository.save(order), new OrderDto());
  }

  @Override
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order not found"));

    if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
      throw new OrderAlreadyProcessedException("Only pending order can be canceled");
    }

    // Update status
    order.setOrderStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);
  }
}
