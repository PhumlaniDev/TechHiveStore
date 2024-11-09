package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * <p> comment </p>.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;
  private String name;
  private String description;
  private String price;
  private Integer quantity;
  private String imageURL;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

}
