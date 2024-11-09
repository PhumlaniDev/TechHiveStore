package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private UsersRepository usersRepository;

  /**
   * <p> This method deletes a user by their ID from the repository. </p>
   *
   * @param id the ID of the user to delete.
   */
  public void deleteUser(Long id) {
    usersRepository.deleteById(id);
  }
}
