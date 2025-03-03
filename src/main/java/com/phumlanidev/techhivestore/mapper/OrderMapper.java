package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.AddressDto;
import com.phumlanidev.techhivestore.dto.OrderDto;
import com.phumlanidev.techhivestore.model.Address;
import com.phumlanidev.techhivestore.model.Order;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Comment: this is the placeholder for documentation.
 */
@Component
@RequiredArgsConstructor
public class OrderMapper {

  private final AddressMapper addressMapper;
  private final OrderItemMapper orderItemMapper;

  /**
   * Comment: this is the placeholder for documentation.
   */

  public Order toEntity(OrderDto dto, Order order) {
    order.setOrderNumber(dto.getOrderNumber() != null ? dto.getOrderNumber() : UUID.randomUUID());
    order.setUserId(dto.getUserId());
    order.setOrderStatus(dto.getOrderStatus());
    order.setTotalPrice(dto.getTotalPrice());
    order.setPaymentStatus(dto.getPaymentStatus());
    order.setAddressId(addressMapper.toEntity(dto.getAddress(), new Address()));

    return order;
  }

  /**
   * Comment: this is the placeholder for documentation.
   */

  public OrderDto toDto(Order order, OrderDto dto) {
    dto.setOrderNumber(order.getOrderNumber());
    dto.setUserId(order.getUserId());
    dto.setOrderStatus(order.getOrderStatus());
    dto.setTotalPrice(order.getTotalPrice());
    dto.setPaymentStatus(order.getPaymentStatus());
    dto.setAddress(addressMapper.toDto(order.getAddressId(), new AddressDto()));
    dto.setItems(
      order.getItems().stream().map(orderItemMapper::toDto).collect(Collectors.toList()));

    return dto;
  }
}
