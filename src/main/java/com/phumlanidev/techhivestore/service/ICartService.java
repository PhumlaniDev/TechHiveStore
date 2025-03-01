package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.model.Cart;

/**
 * Comment: this is the placeholder for documentation.
 */
public interface ICartService {

  /**
   * Comment: this is the placeholder for documentation.
   */
  void addItemToCart(Long userId, Long productId, int quantity);

  /**
   * Comment: this is the placeholder for documentation.
   */
  void removeItemFromCart(Long userId, Long productId);

  /**
   * Comment: this is the placeholder for documentation.
   */
  void clearCart(Long userId);

  /**
   * Comment: this is the placeholder for documentation.
   */
  Cart getCartDetails(Long userId);
}
