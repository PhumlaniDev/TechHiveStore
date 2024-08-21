package com.phumlanidev.techhivestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p> comment </p>.
 */
@Entity
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;
  private String name;
  private String description;
  private String price;
  private Integer quantity;
  private String imageURL;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;
  @ManyToOne
  @JoinColumn(name = "created_by")
  private Users createdBy;
  @ManyToOne
  @JoinColumn(name = "updated_by")
  private Users updatedBy;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
}
