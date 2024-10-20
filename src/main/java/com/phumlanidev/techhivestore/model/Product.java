package com.phumlanidev.techhivestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

/**
 * <p> comment </p>.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class Product extends BaseEntity{

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

}
