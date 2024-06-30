package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long addressId;
  @Column(name = "street_address")
  private String streetAddress;
  @Column(name = "city")
  private String city;
  @Column(name = "province")
  private String province;
  @Column(name = "zip_code")
  private String zipCode;
  @Column(name = "country")
  private String country;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users userId;
}
