package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.UserDTO;
import com.phumlanidev.techhivestore.model.Users;

public class UserMapper extends AbstractMapper<Users, UserDTO> {


    @Override
    public Users toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        Users users = new Users();
        users.setUserId(dto.getUserId());
        users.setUsername(dto.getUsername());
        users.setPassword(dto.getPassword());
        users.setEmail(dto.getEmail());
        users.setFirstName(dto.getFirstName());
        users.setLastName(dto.getLastName());
        users.setPhoneNumber(dto.getPhoneNumber());
        return users;

    }

    @Override
    public UserDTO toDTO(Users entity) {
        return null;
    }
}
