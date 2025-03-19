package com.phumlanidev.techhivestore.service.impl;

import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.exception.cart.CartNotFoundException;
import com.phumlanidev.techhivestore.model.Cart;
import com.phumlanidev.techhivestore.repository.CartRepository;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import com.phumlanidev.techhivestore.repository.UserRepository;
import com.phumlanidev.techhivestore.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Comment: this is the placeholder for documentation.
 */

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;


  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public void addItemToCart(Long userId, Long productId, int quantity) {
    Cart cart = cartRepository.findByUserUserId(userId).orElseGet(() -> {
      Cart newCart = new Cart();
      newCart.setUser(userRepository.findById(userId)
          .orElseThrow(() -> new RuntimeException("User not found")));
      return cartRepository.save(newCart);
    });

    cartRepository.save(cart);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public void removeItemFromCart(Long userId, Long productId) {
    Cart cart = cartRepository.findByUserUserId(userId)
        .orElseThrow(() -> new CartNotFoundException(Constant.CART_NOT_FOUND));

    cartRepository.save(cart);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public void clearCart(Long userId) {
    Cart cart = cartRepository.findByUserUserId(userId)
        .orElseThrow(() -> new CartNotFoundException(Constant.CART_NOT_FOUND));

    cartRepository.save(cart);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public Cart getCartDetails(Long userId) {
    return cartRepository.findByUserUserId(userId)
        .orElseThrow(() -> new CartNotFoundException(Constant.CART_NOT_FOUND));
  }
}
