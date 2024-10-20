package com.phumlanidev.techhivestore.model;

import com.phumlanidev.techhivestore.dto.ProductDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p> comment </p>.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Product extends ProductDTO {

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



  public Product() {

  }

  public Product(Long productId,
                 LocalDateTime updatedDate,
                 LocalDateTime createdDate,
                 Users updatedBy,
                 Users createdBy,
                 Category category,
                 String imageURL,
                 Integer quantity,
                 String price,
                 String description,
                 String name) {
    this.productId = productId;
    this.updatedDate = updatedDate;
    this.createdDate = createdDate;
    this.updatedBy = updatedBy;
    this.createdBy = createdBy;
    this.category = category;
    this.imageURL = imageURL;
    this.quantity = quantity;
    this.price = price;
    this.description = description;
    this.name = name;
  }
}
