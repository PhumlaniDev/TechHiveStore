package com.phumlanidev.techhivestore.auth;

import com.phumlanidev.techhivestore.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Comment: this is the placeholder for documentation.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String role;
  private AddressDto address;
}
