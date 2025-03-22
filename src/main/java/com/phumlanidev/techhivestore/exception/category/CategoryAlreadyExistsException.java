package com.phumlanidev.techhivestore.exception.category;

import com.phumlanidev.techhivestore.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Comment: this is the placeholder for documentation.
 */
public class CategoryAlreadyExistsException extends BaseException {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public CategoryAlreadyExistsException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
