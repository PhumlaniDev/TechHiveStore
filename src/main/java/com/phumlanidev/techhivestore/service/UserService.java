package com.phumlanidev.techhivestore.service;


import com.phumlanidev.techhivestore.dto.UserDTO;
import com.phumlanidev.techhivestore.exception.UserNotFoundException;
import com.phumlanidev.techhivestore.model.Users;
import com.phumlanidev.techhivestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO registerUser(UserDTO userDTO) {
        Users user = new Users();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Collections.singletonList("ROLE_USER"));
        userRepository.save(user);
        return new UserDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) throws UserNotFoundException {
        Users user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) throws UserNotFoundException {
        Users user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return new UserDTO(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
