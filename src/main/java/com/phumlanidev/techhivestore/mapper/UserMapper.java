package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.UserDto;
import com.phumlanidev.techhivestore.model.Users;
import org.springframework.stereotype.Component;

/**
 * Comment: this is the placeholder for documentation.
 */
@Component
public class UserMapper {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public Users toEntity(UserDto dto, Users users) {
    users.setUsername(dto.getUsername());
    users.setEmail(dto.getEmail());
    users.setFirstName(dto.getFirstName());
    users.setLastName(dto.getLastName());
    users.setPhoneNumber(dto.getPhoneNumber());
    users.setPassword(dto.getPassword());
    users.setRole(dto.getRole());
    return users;

  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  public UserDto toDto(Users users, UserDto dto) {
    dto.setUsername(users.getUsername());
    dto.setEmail(users.getEmail());
    dto.setFirstName(users.getFirstName());
    dto.setLastName(users.getLastName());
    dto.setPhoneNumber(users.getPhoneNumber());
    dto.setRole(users.getRole());
    dto.setAddress(dto.getAddress());

    return dto;
  }
}
