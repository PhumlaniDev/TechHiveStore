package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p> comment </p>.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
