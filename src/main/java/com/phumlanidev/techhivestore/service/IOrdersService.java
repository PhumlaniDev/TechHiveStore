package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.dto.OrderDto;
import java.util.List;

/**
 * Comment: this is the placeholder for documentation.
 */
public interface IOrdersService {

  /**
   * Comment: this is the placeholder for documentation.
   * return
   */
  OrderDto placeOrder(Long userId, Long addressId, String paymentMethod);

  /**
   * Comment: this is the placeholder for documentation.
   */
  OrderDto getOrderDetails(Long orderId);

  /**
   * Comment: this is the placeholder for documentation.
   */
  List<OrderDto> getUserOrders(Long userId);

  /**
   * Comment: this is the placeholder for documentation.
   */
  OrderDto updateOrderStatus(Long orderId, String status);

  /**
   * Comment: this is the placeholder for documentation.
   */
  void cancelOrder(Long orderId);

}
