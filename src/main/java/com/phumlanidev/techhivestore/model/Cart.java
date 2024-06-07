package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long cartId;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userId;
  private double totalPrice;
}