package com.phumlanidev.techhivestore.exception.order;

import com.phumlanidev.techhivestore.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Comment: this is the placeholder for documentation.
 */
public class OrderPaymentFailedException extends BaseException {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public OrderPaymentFailedException(String message) {
    super(message, HttpStatus.BAD_GATEWAY);
  }
}
