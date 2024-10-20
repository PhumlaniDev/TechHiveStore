package com.phumlanidev.techhivestore.model;

import com.phumlanidev.techhivestore.enums.Roles;
import jakarta.persistence.*;

import lombok.*;

/**
 * <p> comment </p>.
 */
@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Users extends BaseEntity{

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
  @Enumerated(EnumType.STRING)
  private Roles role;
  @ManyToOne
  @JoinColumn(name = "address_id", referencedColumnName = "addressId")
  private Address address;

}
