package com.phumlanidev.techhivestore.service.impl;

import com.phumlanidev.techhivestore.constant.Constant;
import com.phumlanidev.techhivestore.dto.CartDto;
import com.phumlanidev.techhivestore.exception.ProductNotFoundException;
import com.phumlanidev.techhivestore.exception.cart.CartItemNotFoundException;
import com.phumlanidev.techhivestore.exception.cart.CartNotFoundException;
import com.phumlanidev.techhivestore.mapper.CartMapper;
import com.phumlanidev.techhivestore.model.Cart;
import com.phumlanidev.techhivestore.model.CartItem;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.model.User;
import com.phumlanidev.techhivestore.repository.CartItemRepository;
import com.phumlanidev.techhivestore.repository.CartRepository;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import com.phumlanidev.techhivestore.service.ICartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Comment: this is the placeholder for documentation.
 */

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductRepository productRepository;
  private final CartMapper cartMapper;

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public CartDto getCartByUser(User user) {

    Cart cart = getOrCreateCart(user);
    return cartMapper.toDto(cart, new CartDto());
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public void addProductToCart(User user, Long productId, Integer quantity) {
    Cart cart = getOrCreateCart(user);
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(Constant.PRODUCT_NOT_FOUND));
    CartItem cartItem = cartItemRepository.findAll().stream().filter(
        item -> item.getCart().getCartId().equals(cart.getCartId()) &&
            item.getProduct().getProductId().equals(productId)).findFirst().orElse(new CartItem());

    if (cartItem.getCartItemsId() == null) {
      cartItem.setCart(cart);
      cartItem.setProduct(product);
      cartItem.setQuantity(quantity);
      cartItem.setPrice(product.getPrice());
    } else {
      cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }

    cartItemRepository.save(cartItem);
    recalculateCartTotal(cart);

  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public void removeCartItem(User user, Long cartItemId) {
    Cart cart = cartRepository.findByUserUserId(user.getUserId())
        .orElseThrow(() -> new CartNotFoundException(Constant.CART_NOT_FOUND));
    CartItem cartItem = cartItemRepository.findById(cartItemId)
        .orElseThrow(() -> new CartItemNotFoundException(Constant.CART_ITEM_NOT_FOUND));

    if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
      throw new CartItemNotFoundException(Constant.CART_ITEM_NOT_FOUND);
    }

    cartItemRepository.delete(cartItem);
    recalculateCartTotal(cart);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public Cart getOrCreateCart(User user) {
    return cartRepository.findByUserUserId(user.getUserId()).orElseGet(() -> {
      Cart newCart = new Cart();
      newCart.setUser(user);
      newCart.setTotalPrice(0);
      return cartRepository.save(newCart);
    });
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public void clearCart(User user) {
    Cart cart = cartRepository.findByUserUserId(user.getUserId())
        .orElseThrow(() -> new CartNotFoundException(Constant.CART_NOT_FOUND));

    List<CartItem> cartItems = cartItemRepository.findAll().stream()
        .filter(item -> item.getCart().getCartId().equals(cart.getCartId())).toList();

    cartItemRepository.deleteAll(cartItems);
    cart.setTotalPrice(0);
    recalculateCartTotal(cart);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public void updateCartItemQuantity(User user, Long cartItemId, Integer quantity) {
    Cart cart = cartRepository.findByUserUserId(user.getUserId())
        .orElseThrow(() -> new CartNotFoundException(Constant.CART_NOT_FOUND));
    CartItem cartItem = cartItemRepository.findById(cartItemId)
        .orElseThrow(() -> new CartItemNotFoundException(Constant.CART_ITEM_NOT_FOUND));

    if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
      throw new CartItemNotFoundException(Constant.CART_ITEM_NOT_FOUND);
    }

    cartItem.setQuantity(quantity);
    cartItemRepository.save(cartItem);
    recalculateCartTotal(cart);
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Override
  public void recalculateCartTotal(Cart cart) {
    List<CartItem> items = cartItemRepository.findAll().stream()
        .filter(item -> item.getCart().getCartId().equals(cart.getCartId())).toList();

    double total = items.stream()
        .mapToDouble(item -> item.getProduct().getPrice().doubleValue() * item.getQuantity()).sum();
    cart.setTotalPrice(total);
    cartRepository.save(cart);
  }
}
