package com.phumlanidev.techhivestore.mapper;

import com.phumlanidev.techhivestore.dto.AddressDTO;
import com.phumlanidev.techhivestore.model.Address;

public class AddressMapper extends AbstractMapper<Address, AddressDTO> {

  @Override
  public Address toEntity(AddressDTO dto) {
    Address address = new Address();
    address.setStreetName(dto.getStreetName());
    address.setCity(dto.getCity());
    address.setProvince(dto.getProvince());
    address.setZipCode(dto.getZipCode());
    address.setCountry(dto.getCountry());
    return address;
  }

  @Override
  public AddressDTO toDTO(Address entity) {
    AddressDTO addressDTO = new AddressDTO();
    addressDTO.setStreetName(entity.getStreetName());
    addressDTO.setCity(entity.getCity());
    addressDTO.setProvince(entity.getProvince());
    addressDTO.setZipCode(entity.getZipCode());
    addressDTO.setCountry(entity.getCountry());
    return addressDTO;
  }
}
