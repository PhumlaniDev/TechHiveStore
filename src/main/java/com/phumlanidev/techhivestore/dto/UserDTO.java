package com.phumlanidev.techhivestore.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long addressId;
}
