package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.dto.UserDTO;
import com.phumlanidev.techhivestore.exception.UserNotFoundException;
import com.phumlanidev.techhivestore.model.Users;
import com.phumlanidev.techhivestore.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

  private final UsersRepository usersRepository;

  public AdminService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  /**
   * <p> comment </p>.
   */
  public List<UserDTO> getAllUsers() {
    return usersRepository.findAll().stream()
        .map(UserDTO::new).collect(Collectors.toList());
  }

  /**
   * <p> comment </p>.
   */
  public UserDTO getUserById(Long id) throws UserNotFoundException {
    Users user = usersRepository.findById(id).orElseThrow(() ->
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

  /**
   * <p> This method deletes a user by their ID from the repository. </p>
   *
   * @param id the ID of the user to delete.
   */
  public void deleteUser(Long id) {
    usersRepository.deleteById(id);
  }
}
