package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.AddressDto;
import com.phumlanidev.techhivestore.model.Address;

public class AddressMapper{

  public Address toEntity(AddressDto dto, Address address) {
    address.setStreetName(dto.getStreetName());
    address.setCity(dto.getCity());
    address.setProvince(dto.getProvince());
    address.setZipCode(dto.getZipCode());
    address.setCountry(dto.getCountry());
    return address;
  }


  public AddressDto toDTO(Address entity, AddressDto addressDTO ) {
    addressDTO.setStreetName(entity.getStreetName());
    addressDTO.setCity(entity.getCity());
    addressDTO.setProvince(entity.getProvince());
    addressDTO.setZipCode(entity.getZipCode());
    addressDTO.setCountry(entity.getCountry());
    return addressDTO;
  }
}
