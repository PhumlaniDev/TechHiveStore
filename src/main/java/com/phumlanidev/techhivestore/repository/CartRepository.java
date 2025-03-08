package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.model.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Comment: this is the placeholder for documentation.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  /**
   * Comment: this is the placeholder for documentation.
   */
  Optional<Cart> findByUserUserId(Long userId);

}
