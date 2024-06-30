package com.phumlanidev.techhivestore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long orderItemId;
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Orders oderId; // foreign key
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product productId; // foreign key
  @Column(name = "quantity")
  private Integer quantity;
  @Column(name = "price")
  private double price;


}