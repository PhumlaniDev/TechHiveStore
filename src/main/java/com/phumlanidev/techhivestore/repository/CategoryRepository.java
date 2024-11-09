package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p> comment </p>.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  Optional<Category> findByCategoryName(String categoryName);
}
