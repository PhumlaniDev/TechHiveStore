package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.dto.CartDto;
import com.phumlanidev.techhivestore.model.Cart;
import com.phumlanidev.techhivestore.model.User;

/**
 * Comment: this is the placeholder for documentation.
 */
public interface ICartService {

  /**
   * Comment: this is the placeholder for documentation.
   */
  CartDto getCartByUser(User user);

  /**
   * Comment: this is the placeholder for documentation.
   */
  void addProductToCart(User user, Long productId, Integer quantity);

  /**
   * Comment: this is the placeholder for documentation.
   */
  void removeCartItem(User user, Long cartItemId);

  /**
   * Comment: this is the placeholder for documentation.
   */
  Cart getOrCreateCart(User user);

  /**
   * Comment: this is the placeholder for documentation.
   */
  void clearCart(User user);

  /**
   * Comment: this is the placeholder for documentation.
   */
  void updateCartItemQuantity(User user, Long cartItemId, Integer quantity);

  /**
   * Comment: this is the placeholder for documentation.
   */
  void recalculateCartTotal(Cart cart);
}
