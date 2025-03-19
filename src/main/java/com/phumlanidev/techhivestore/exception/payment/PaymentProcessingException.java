package com.phumlanidev.techhivestore.exception.payment;

import com.phumlanidev.techhivestore.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Comment: this is the placeholder for documentation.
 */
public class PaymentProcessingException extends BaseException {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public PaymentProcessingException(String message) {
    super(message, HttpStatus.BAD_GATEWAY);
  }
}
