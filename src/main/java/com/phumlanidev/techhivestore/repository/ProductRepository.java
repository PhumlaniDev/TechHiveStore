package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.dto.ProductDto;
import com.phumlanidev.techhivestore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<ProductDto> findByName(String productName);
}
