package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.CartDto;
import com.phumlanidev.techhivestore.dto.CartItemDto;
import com.phumlanidev.techhivestore.model.Cart;
import com.phumlanidev.techhivestore.model.CartItem;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * Comment: this is the placeholder for documentation.
 */
@Component
@RequiredArgsConstructor
public class CartMapper {
  
  private final CartItemMapper cartItemMapper;
  
  /**
   * Comment: this is the placeholder for documentation.
   */
  public Cart toEntity(CartDto cartDto, Cart cart) {
    cart.setCartId(cartDto.getCartId());
    cart.setUser(cartDto.getUser());
    cart.setCartItems(cartDto.getCartItemDtoList().stream()
            .map(cartItemDto -> cartItemMapper.toEntity(new CartItem(), cartItemDto))
            .collect(Collectors.toList()));
    return cart;
  }
  
  /**
   * Comment: this is the placeholder for documentation.
   */
  public CartDto toDto(Cart cart, CartDto cartDto) {
    cartDto.setCartId(cart.getCartId());
    cartDto.setUser(cart.getUser());
    cartDto.setCartItemDtoList(cart.getCartItems().stream()
            .map(cartItemDto -> cartItemMapper.toDto(cartItemDto, new CartItemDto()))
            .collect(Collectors.toList()));
    return cartDto;
  }
}
