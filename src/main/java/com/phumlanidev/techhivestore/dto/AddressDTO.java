package com.phumlanidev.techhivestore.dto;

import lombok.*;

@Data
public class AddressDTO {

  private Long addressId;
  private String streetAddress;
  private String city;
  private String province;
  private String zipCode;
  private String country;
  private Long userId;
}
