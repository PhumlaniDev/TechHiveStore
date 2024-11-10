package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Comment: this is the placeholder for documentation.
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

  /**
   * Comment: this is the placeholder for documentation.
   */
  Users findByUsername(String username);
}
