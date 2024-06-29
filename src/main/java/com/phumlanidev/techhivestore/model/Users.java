package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  @ManyToOne
  private Address addressId;

}
