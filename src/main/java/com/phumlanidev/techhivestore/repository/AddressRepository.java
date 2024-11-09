package com.phumlanidev.techhivestore.repository;

import com.phumlanidev.techhivestore.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p> comment </p>.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
