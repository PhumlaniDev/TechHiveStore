package com.phumlanidev.techhivestore.controller;

import com.phumlanidev.techhivestore.dto.CartDto;
import com.phumlanidev.techhivestore.model.User;
import com.phumlanidev.techhivestore.service.impl.CartServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping(path = "/api/v1/cart", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(name = "Cart Management", description = "Endpoints for related user's shopping cart operations.")
public class CartController {

  /**
   * Comment: this is the placeholder for documentation.
   */
  @Autowired
  public CartController(CartServiceImpl cartServiceImpl) {
    this.cartServiceImpl = cartServiceImpl;
  }

  private final CartServiceImpl cartServiceImpl;

  @Operation(summary = "Get user's cart", description = "Returns the current user's cart details.")
  @ApiResponse(responseCode = "200", description = "Cart retrieved successfully.")
  @GetMapping
  public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal User user) {
    CartDto cart = cartServiceImpl.getCartByUser(user);
    return ResponseEntity.ok(cart);
  }

  @Operation(summary = "Add product to cart", description = "Adds a product to the current user's cart.")
  @ApiResponse(responseCode = "200", description = "Product added to cart successfully.")
  @PostMapping("/add")
  public ResponseEntity<Void> addProductToCart(@RequestParam Long productId,
                                               @RequestParam int quantity,
                                               @AuthenticationPrincipal User user) {
    cartServiceImpl.addProductToCart(user, productId, quantity);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Update cart item quantity", description = "Update quantity of a specific item in the user's cart.")
  @PutMapping("/update/{cartItemId}")
  public ResponseEntity<Void> updateCartItemQuantity(@PathVariable Long cartItemId,
                                                     @RequestParam int quantity,
                                                     Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    cartServiceImpl.updateCartItemQuantity(user, cartItemId, quantity);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Remove cart item", description = "Removes a product from user's cart.")
  @ApiResponse(responseCode = "200", description = "Cart item removed.")
  @DeleteMapping("/remove/{cartItemId}")
  public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId,
                                             @AuthenticationPrincipal User user) {
    cartServiceImpl.removeCartItem(user, cartItemId);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Clear cart", description = "Clears all items from the user's cart.")
  @ApiResponse(responseCode = "200", description = "Cart cleared successfully.")
  @DeleteMapping("/clear")
  public ResponseEntity<Void> clearCart(@AuthenticationPrincipal User user) {
    cartServiceImpl.clearCart(user);
    return ResponseEntity.ok().build();
  }
}
