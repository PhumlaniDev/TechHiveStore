package com.phumlanidev.techhivestore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Comment: this is the placeholder for documentation.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CategoryAlreadyExistException extends RuntimeException {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public CategoryAlreadyExistException(String message) {
    super(message);
  }
}
