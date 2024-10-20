package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.enums.Roles;
import lombok.*;

/**
 * <p> comment </p>.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private Roles role;
  private AddressDto address;
}
