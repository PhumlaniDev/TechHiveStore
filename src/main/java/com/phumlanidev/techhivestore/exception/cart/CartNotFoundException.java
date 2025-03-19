package com.phumlanidev.techhivestore.exception.cart;

import com.phumlanidev.techhivestore.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Comment: this is the placeholder for documentation.
 */
public class CartNotFoundException extends BaseException {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public CartNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
