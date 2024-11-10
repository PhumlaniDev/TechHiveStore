package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.AddressDto;
import com.phumlanidev.techhivestore.model.Address;
import org.springframework.stereotype.Component;

/**
 * Comment: this is the placeholder for documentation.
 */
@Component
public class AddressMapper {

  /**
   * Comment: this is the placeholder for documentation.
   */
  public Address toEntity(AddressDto dto, Address address) {
    address.setStreetName(dto.getStreetName());
    address.setCity(dto.getCity());
    address.setProvince(dto.getProvince());
    address.setZipCode(dto.getZipCode());
    address.setCountry(dto.getCountry());
    return address;
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  public AddressDto toDto(Address entity, AddressDto addressDto) {
    addressDto.setStreetName(entity.getStreetName());
    addressDto.setCity(entity.getCity());
    addressDto.setProvince(entity.getProvince());
    addressDto.setZipCode(entity.getZipCode());
    addressDto.setCountry(entity.getCountry());
    return addressDto;
  }
}
