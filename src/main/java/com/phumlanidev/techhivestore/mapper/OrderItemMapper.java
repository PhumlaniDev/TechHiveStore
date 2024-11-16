package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.OrderItemDto;
import com.phumlanidev.techhivestore.model.OrderItem;
import org.springframework.stereotype.Component;

/**
 * Comment: this is the placeholder for documentation.
 */

@Component
public class OrderItemMapper {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public OrderItem toEntity(OrderItemDto dto, OrderItem orderItem) {
    orderItem.setQuantity(dto.getQuantity());
    orderItem.setPrice(dto.getPrice());
    return orderItem;
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  public OrderItemDto toDto(OrderItem orderItem) {
    OrderItemDto dto = new OrderItemDto();
    dto.setOrderItemId(orderItem.getOrderItemId());
    dto.setOrderId(orderItem.getOrderId().getOrderId());
    dto.setProductId(orderItem.getProductId().getProductId());
    dto.setProductName(orderItem.getProductId().getName());
    dto.setQuantity(orderItem.getQuantity());
    dto.setPrice(orderItem.getPrice());
    return dto;
  }
}
