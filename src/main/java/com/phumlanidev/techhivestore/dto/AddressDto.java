package com.phumlanidev.techhivestore.dto;

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
}
