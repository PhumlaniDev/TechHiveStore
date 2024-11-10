package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Comment: this is the placeholder for documentation.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
