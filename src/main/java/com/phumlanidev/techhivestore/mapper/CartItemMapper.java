package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.CartItemDto;
import com.phumlanidev.techhivestore.model.CartItem;
import org.springframework.stereotype.Component;

/**
 * Comment: this is the placeholder for documentation.
 */
@Component
public class CartItemMapper {
  
  /**
   * Comment: this is the placeholder for documentation.
   */
  public CartItem toEntity(CartItem cartItem, CartItemDto cartItemDto) {
    cartItem.setQuantity(cartItemDto.getQuantity());
    cartItem.setProduct(cartItemDto.getProductId());
    cartItem.setPrice(cartItemDto.getPrice());
    cartItem.setCart(cartItemDto.getCart());
    return cartItem;
  }
  
  /**
   * Comment: this is the placeholder for documentation.
   */
  public CartItemDto toDto(CartItem cartItem, CartItemDto cartItemDto) {
    cartItemDto.setProductId(cartItem.getProduct());
    cartItemDto.setQuantity(cartItem.getQuantity());
    cartItemDto.setPrice(cartItemDto.getPrice());
    cartItemDto.setCart(cartItem.getCart());
    return cartItemDto;
  }
}
