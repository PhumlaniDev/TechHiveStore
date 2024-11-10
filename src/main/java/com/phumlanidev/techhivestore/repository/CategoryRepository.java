package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.model.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Comment: this is the placeholder for documentation.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  /**
   * Comment: this is the placeholder for documentation.
   */
  Optional<Category> findByCategoryName(String categoryName);
}
