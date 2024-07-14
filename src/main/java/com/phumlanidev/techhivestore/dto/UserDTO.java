package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.model.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

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
//    private Long addressId;

    public UserDTO(Users user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
