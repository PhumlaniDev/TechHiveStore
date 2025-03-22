package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.dto.LoginDto;
import com.phumlanidev.techhivestore.dto.UserDto;

/**
 * Comment: this is the placeholder for documentation.
 */
public interface IAuthService {

  /**
   * Comment: this is the placeholder for documentation.
   */
  void registerUser(UserDto userDto);

  /**
   * Comment: this is the placeholder for documentation.
   */
  String login(LoginDto loginDto);

}
