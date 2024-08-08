package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.dto.UserDTO;
import com.phumlanidev.techhivestore.exception.UserNotFoundException;
import com.phumlanidev.techhivestore.model.Users;
import com.phumlanidev.techhivestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

  private final UserRepository userRepository;

  public AdminService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * <p> comment </p>.
   */
  public List<UserDTO> getAllUsers() {
    return userRepository.findAll().stream()
        .map(UserDTO::new).collect(Collectors.toList());
  }

  /**
   * <p> comment </p>.
   */
  public UserDTO getUserById(Long id) throws UserNotFoundException {
    Users user = userRepository.findById(id).orElseThrow(() ->
        new UserNotFoundException("User not found"));
    return new UserDTO(user);
  }

  /**
   * <p> comment </p>.
   */
//  public UserDTO updateUser(Long id, UserDTO userDTO) throws UserNotFoundException {
//    Users user = userRepository.findById(id).orElseThrow(() ->
//        new UserNotFoundException("User not found"));
//    user.setUsername(userDTO.getUsername());
//    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//    userRepository.save(user);
//    return new UserDTO(user);
//  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
