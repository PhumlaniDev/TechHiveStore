package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long productId;
  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;
  @Column(name = "price")
  private String price;
  @Column(name = "quantity")
  private Integer quantity;
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
  @Column(name = "image_url")
  private String imageURL;
}
