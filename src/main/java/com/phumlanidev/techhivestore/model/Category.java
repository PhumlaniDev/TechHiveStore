package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long categoryId;
  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;
}
