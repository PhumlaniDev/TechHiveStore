package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long cartItemsId;
  @ManyToOne
  @JoinColumn(name = "cart_id")
  private Cart cart;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  private Integer quantity;
  private Integer price;
}