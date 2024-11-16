package com.phumlanidev.techhivestore.service.impl;

import com.phumlanidev.techhivestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Comment: this is the placeholder for documentation.
 */
@Service
@RequiredArgsConstructor
public class AdminService {

  private UserRepository userRepository;

  /**
   * Comment: this is the placeholder for documentation.
   */
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
