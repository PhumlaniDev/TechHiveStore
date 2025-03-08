package com.phumlanidev.techhivestore.controller;


import com.phumlanidev.techhivestore.dto.OrderDto;
import com.phumlanidev.techhivestore.service.impl.OrdersServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Comment: this is the placeholder for documentation.
 */
@RestController
@RequestMapping(path = "/api/v1/orders", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class OrderController {

  private final OrdersServiceImpl ordersService;

  /**
   * Comment: this is the placeholder for documentation.
   */
  @PostMapping("/place-order")
  public ResponseEntity<OrderDto> placeOrder(
      @RequestParam Long userId,
      @RequestParam Long addressId) {
    OrderDto createdOrder = ordersService.placeOrder(userId, addressId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(createdOrder);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDto> getOrderDetails(@PathVariable Long orderId) {
    OrderDto orderDto = ordersService.getOrderDetails(orderId);
    return ResponseEntity.ok(orderDto);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Long userId) {
    List<OrderDto> orders = ordersService.getUserOrders(userId);
    return ResponseEntity.ok(orders);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @PutMapping("/{orderId}/status")
  public ResponseEntity<OrderDto> updateOrderStatus(
      @PathVariable Long orderId,
      @RequestParam String status) {
    OrderDto updatedOrder = ordersService.updateOrderStatus(orderId, status);
    return ResponseEntity.ok(updatedOrder);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @DeleteMapping("/{orderId}/cancel")
  public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
    ordersService.cancelOrder(orderId);
    return ResponseEntity.noContent().build();
  }
}
