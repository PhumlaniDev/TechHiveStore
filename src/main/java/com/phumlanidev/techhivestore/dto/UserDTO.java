package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.mapper.AddressMapper;
import com.phumlanidev.techhivestore.model.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> comment </p>.
 */
@Data
@NoArgsConstructor
public class UserDTO {

  private Long userId;
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private AddressDTO address;


  /**
   * <p> comment </p>.
   */
  public UserDTO(Users user) {
    this.userId = user.getUserId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.phoneNumber = user.getPhoneNumber();

    if (user.getAddressId() != null) {
      AddressMapper addressMapper = new AddressMapper();
      this.address = addressMapper.toDTO(user.getAddressId());
    }
  }
}
