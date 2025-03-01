package com.phumlanidev.techhivestore.service.impl;

import com.phumlanidev.techhivestore.model.Cart;
import com.phumlanidev.techhivestore.model.CartItem;
import com.phumlanidev.techhivestore.model.Product;
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
public class CartService  implements ICartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  /**
   * Comment: this is the placeholder for documentation.
   *
   * @param userId
   * @param productId
   * @param quantity
   */
  @Override
  public void addItemToCart(Long userId, Long productId, int quantity) {
    Cart cart = cartRepository.findByUser_UserId(userId)
      .orElseGet(() -> {
        Cart newCart = new Cart();
        newCart.setUser(userRepository.findById(userId)
          .orElseThrow(() -> new RuntimeException("User not found")));
        return cartRepository.save(newCart);
      });

    Product product = productRepository.findById(productId)
      .orElseThrow(() -> new RuntimeException("Product not found"));

    CartItem cartItem = cart.getCartItems().stream()
      .filter(item -> item.getProduct().getProductId().equals(productId))
      .findFirst()
      .orElseGet(() -> {
        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(0);
        cart.getCartItems().add(newItem);
        return newItem;
      });

    cartItem.setQuantity(cartItem.getQuantity() + quantity);
    cartRepository.save(cart);
  }

  /**
   * Comment: this is the placeholder for documentation.
   *
   * @param userId
   * @param productId
   */
  @Override
  public void removeItemFromCart(Long userId, Long productId) {
    Cart cart = cartRepository.findByUser_UserId(userId)
      .orElseThrow(() -> new RuntimeException("Cart not found"));

    CartItem cartItem = cart.getCartItems().stream()
      .filter(item -> item.getProduct().getProductId().equals(productId))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Product not found in cart"));

    cart.getCartItems().remove(cartItem);
    cartRepository.save(cart);
  }

  /**
   * Comment: this is the placeholder for documentation.
   *
   * @param userId
   */
  @Override
  public void clearCart(Long userId) {
    Cart cart = cartRepository.findByUser_UserId(userId)
      .orElseThrow(() -> new RuntimeException("Cart not found"));

    cart.getCartItems().clear();
    cartRepository.save(cart);
  }

  /**
   * Comment: this is the placeholder for documentation.
   *
   * @param userId
   */
  @Override
  public Cart getCartDetails(Long userId) {
    return cartRepository.findByUser_UserId(userId)
      .orElseThrow(() -> new RuntimeException("Cart not found"));
  }
}
