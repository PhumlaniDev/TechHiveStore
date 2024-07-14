package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;
  @Column(unique = true)
  private String username;
  private String password;
  @Column(unique = true)
  private String email;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roles;
  @ManyToOne
  private Address addressId;

}
