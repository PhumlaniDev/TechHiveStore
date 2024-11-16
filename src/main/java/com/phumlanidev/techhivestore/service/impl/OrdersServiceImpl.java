package com.phumlanidev.techhivestore.service.impl;

import com.phumlanidev.techhivestore.dto.OrderDto;
import com.phumlanidev.techhivestore.enums.OrderStatus;
import com.phumlanidev.techhivestore.enums.PaymentStatus;
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
import com.phumlanidev.techhivestore.service.IOrdersService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
  public OrderDto placeOrder(Long userId, Long addressId, String paymentMethod) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("user not found"));

    final Address address = addressRepository.findById(addressId)
        .orElseThrow(() -> new RuntimeException("Address not found"));

    // Fetch cart
    Cart cart = cartRepository.findByUser(user)
        .orElseThrow(() -> new RuntimeException("Cart not found"));

    if (cart.getTotalPrice() <= 0) {
      throw new RuntimeException("Cart is empty");
    }

    // Create order entity
    Order order = new Order();
    order.setUserId(user);
    order.setOrderNumber(UUID.randomUUID());
    order.setOrderStatus(OrderStatus.PENDING);
    order.setPaymentStatus(PaymentStatus.PENDING);
    order.setTotalPrice(cart.getTotalPrice());
    order.setAddressId(address);

    //Convert cart item to order items
    List<OrderItem> orderItems = new ArrayList<>();
    for (CartItem cartItem : cart.getCartItems()) {
      Product product = cartItem.getProduct();

      //Reduce stock
      if (product.getQuantity() < cartItem.getQuantity()) {
        throw new RuntimeException("Insufficient stock for product: " + product.getName());
      }
      product.setQuantity(product.getQuantity() - cartItem.getQuantity());
      productRepository.save(product);

      // Create order item
      OrderItem orderItem = new OrderItem();
      orderItem.setOrderId(order);
      orderItem.setProductId(product);
      orderItem.setQuantity(cartItem.getQuantity());
      orderItem.setPrice(cartItem.getQuantity() * Double.parseDouble(product.getPrice()));
      orderItems.add(orderItem);
    }

    order.setItems(orderItems);

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
        .orElseThrow(() -> new RuntimeException("Order not found"));
    return orderMapper.toDto(order, new OrderDto());
  }

  @Transactional
  @Override
  public List<OrderDto> getUserOrders(Long userId) {
    List<Order> orders = orderRepository.findByUserId(userId);
    return orders.stream()
        .map(order -> orderMapper.toDto(order, new OrderDto()))
        .collect(Collectors.toList());
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
        .orElseThrow(() -> new RuntimeException("Order not found"));

    if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
      throw new RuntimeException("Only pending orders can be canceled");
    }

    // Refund stock
    for (OrderItem orderItem : order.getItems()) {
      Product product = orderItem.getProductId();
      product.setQuantity(product.getQuantity() + orderItem.getQuantity());
      productRepository.save(product);
    }

    // Update status
    order.setOrderStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);
  }
}
