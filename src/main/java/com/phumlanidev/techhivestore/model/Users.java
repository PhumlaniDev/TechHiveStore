package com.phumlanidev.techhivestore.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.Data;

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
