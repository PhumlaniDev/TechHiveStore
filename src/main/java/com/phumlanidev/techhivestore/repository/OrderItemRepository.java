package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p> comment </p>.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
