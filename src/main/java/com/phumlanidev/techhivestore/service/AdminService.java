package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

  private final UsersRepository usersRepository;

  /**
   * <p> comment </p>.
   */
//  public List<UserDto> getAllUsers() {
//    return usersRepository.findAll().stream()
//        .map(UserDto::new).collect(Collectors.toList());
//  }
//
//  /**
//   * <p> comment </p>.
//   */
//  public UserDto getUserById(Long id) throws UserNotFoundException {
//    Users user = usersRepository.findById(id).orElseThrow(() ->
//        new UserNotFoundException("User not found"));
//    return new UserDto(user);
//  }
//
//  /**
//   * <p> comment </p>.
//   */
////  public UserDTO updateUser(Long id, UserDTO userDTO) throws UserNotFoundException {
////    Users user = userRepository.findById(id).orElseThrow(() ->
////        new UserNotFoundException("User not found"));
////    user.setUsername(userDTO.getUsername());
////    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
////    userRepository.save(user);
////    return new UserDTO(user);
////  }
//
//  /**
//   * <p> This method deletes a user by their ID from the repository. </p>
//   *
//   * @param id the ID of the user to delete.
//   */
//  public void deleteUser(Long id) {
//    usersRepository.deleteById(id);
//  }
}
