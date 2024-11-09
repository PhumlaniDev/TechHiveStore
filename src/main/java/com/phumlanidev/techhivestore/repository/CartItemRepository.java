package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p> comment </p>.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
