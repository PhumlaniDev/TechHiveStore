package com.phumlanidev.techhivestore.repository;


import com.phumlanidev.techhivestore.model.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Comment: this is the placeholder for documentation.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  /**
   * Comment: this is the placeholder for documentation.
   */
  Optional<Product> findByName(String productName);

  /**
   * Comment: this is the placeholder for documentation.
   */
  Page<Product> findByNameContainingIgnoreCase(String productName, Pageable pageable);
}
