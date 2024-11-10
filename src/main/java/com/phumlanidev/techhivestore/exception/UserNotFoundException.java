package com.phumlanidev.techhivestore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Comment: this is the placeholder for documentation.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public UserNotFoundException(String message) {
    super(message);
  }
}
