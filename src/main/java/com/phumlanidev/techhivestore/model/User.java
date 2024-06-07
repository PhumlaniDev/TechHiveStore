package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long userId;
  @Column(name = "username")
  private String username;
  @Column(name = "password")
  private String password;
  @Column(name = "email")
  private String email;
  @Column(name = "firstName")
  private String firstName;
  @Column(name = "lastName")
  private String lastName;
  @Column(name = "phone_number")
  private String phoneNumber;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private Address addressId;

}
