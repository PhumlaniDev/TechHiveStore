package com.phumlanidev.techhivestore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.phumlanidev.techhivestore.dto.CartDto;
import com.phumlanidev.techhivestore.exception.ProductNotFoundException;
import com.phumlanidev.techhivestore.exception.cart.CartItemNotFoundException;
import com.phumlanidev.techhivestore.mapper.CartMapper;
import com.phumlanidev.techhivestore.model.Cart;
import com.phumlanidev.techhivestore.model.CartItem;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.model.User;
import com.phumlanidev.techhivestore.repository.CartItemRepository;
import com.phumlanidev.techhivestore.repository.CartRepository;
import com.phumlanidev.techhivestore.repository.ProductRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Comment: this is the placeholder for documentation.
 */
class CartServiceTest {

  @InjectMocks
  private CartServiceImpl cartServiceImpl;

  @Mock
  private CartRepository cartRepository;
  @Mock
  private CartItemRepository cartItemRepository;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private CartMapper cartMapper;

  private User user;
  private Cart cart;
  private Product product;
  private CartItem cartItem;

  @BeforeEach
  void setUp() {
    try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {

      user = new User();
      user.setUserId(1L);
      cart = new Cart();
      cart.setCartId(1L);
      cart.setUser(user);
      product = new Product();
      product.setProductId(1L);
      product.setPrice(100);
      cartItem = new CartItem();
      cartItem.setCartItemsId(1L);
      cartItem.setProduct(product);
      cartItem.setCart(cart);
      cartItem.setQuantity(1);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void getCartByUser_shouldReturnCartDto() {
    // given
    when(cartRepository.findByUserUserId(user.getUserId())).thenReturn(Optional.of(cart));
    when(cartMapper.toDto(cart, new CartDto())).thenReturn(new CartDto());

    // when
    CartDto result = cartServiceImpl.getCartByUser(user);

    // then
    assertNotNull(result);
    verify(cartRepository).findByUserUserId(user.getUserId());
    verify(cartMapper).toDto(eq(cart), any(CartDto.class));
  }

  @Test
  void addProductToCart_shouldAddNewItem() {
    when(cartRepository.findByUserUserId(user.getUserId())).thenReturn(Optional.of(cart));
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    when(cartItemRepository.findAll()).thenReturn(Collections.emptyList());

    cartServiceImpl.addProductToCart(user, 1L, 2);

    verify(cartItemRepository).save(any(CartItem.class));
    verify(cartRepository).save(any(Cart.class));
  }

  @Test
  void addProductToCart_shouldThrowProductNotFoundException() {
    when(cartRepository.findByUserUserId(user.getUserId())).thenReturn(Optional.of(cart));
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class,
        () -> cartServiceImpl.addProductToCart(user, 1L, 2));
  }

  @Test
  void removeCartItem_shouldRemoveItem() {
    when(cartRepository.findByUserUserId(user.getUserId())).thenReturn(Optional.of(cart));
    when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));

    cartServiceImpl.removeCartItem(user, 1L);

    verify(cartItemRepository).delete(cartItem);
    verify(cartRepository).save(cart);
  }

  @Test
  void removeCartItem_shouldThrowCartItemNotFound() {
    when(cartRepository.findByUserUserId(user.getUserId())).thenReturn(Optional.of(cart));
    when(cartItemRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(CartItemNotFoundException.class, () -> cartServiceImpl.removeCartItem(user, 1L));
  }

  @Test
  void updateCartItemQuantity_shouldUpdateQuantity() {
    when(cartRepository.findByUserUserId(user.getUserId())).thenReturn(Optional.of(cart));
    when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));

    cartServiceImpl.updateCartItemQuantity(user, 1L, 5);

    assertEquals(5, cartItem.getQuantity());
    verify(cartItemRepository).save(cartItem);
  }

  @Test
  void clearCart_shouldClearAllItems() {
    when(cartRepository.findByUserUserId(user.getUserId())).thenReturn(Optional.of(cart));
    when(cartItemRepository.findAll()).thenReturn(List.of(cartItem));

    cartServiceImpl.clearCart(user);

    verify(cartItemRepository).deleteAll(anyList());
    verify(cartRepository).save(cart);
  }
}