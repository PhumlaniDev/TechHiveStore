package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.model.Address;
import lombok.*;

/**
 * <p> comment </p>.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

  private String streetName;
  private String city;
  private String province;
  private String zipCode;
  private String country;

//  public AddressDto(Address address) {
//    if (address != null) {
//      this.addressId = address.getAddressId();
//      this.streetName = address.getStreetName();
//      this.city = address.getCity();
//      this.province = address.getProvince();
//      this.zipCode = address.getZipCode();
//      this.country = address.getCountry();
//    }
//  }
}
