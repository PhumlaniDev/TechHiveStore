package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.model.Address;
import com.phumlanidev.techhivestore.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> comment </p>.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

  private Long addressId;
  private String streetAddress;
  private String city;
  private String province;
  private String zipCode;
  private String country;

  public AddressDTO(Address address) {
    if (address != null) {
      this.addressId = address.getAddressId();
      this.streetAddress = address.getStreetAddress();
      this.city = address.getCity();
      this.province = address.getProvince();
      this.zipCode = address.getZipCode();
      this.country = address.getCountry();
    }
  }
}
