package com.phumlanidev.techhivestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> comment </p>.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

  private String username;
  private String password;
}
