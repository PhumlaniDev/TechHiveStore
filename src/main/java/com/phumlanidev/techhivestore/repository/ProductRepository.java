package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.dto.ProductDTO;
import com.phumlanidev.techhivestore.model.Product;
import com.phumlanidev.techhivestore.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<ProductDTO> findByName(ProductDTO productName);
}
