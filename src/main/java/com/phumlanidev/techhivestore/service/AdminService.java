package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Comment: this is the placeholder for documentation.
 */
@Service
@RequiredArgsConstructor
public class AdminService {

  private UsersRepository usersRepository;

  /**
   * Comment: this is the placeholder for documentation.
   */
  public void deleteUser(Long id) {
    usersRepository.deleteById(id);
  }
}
