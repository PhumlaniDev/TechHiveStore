package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p> comment </p>.
 */
@Entity
@Data
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryId;
  private String categoryName;
  private String description;
  @ManyToOne
  @JoinColumn(name = "created_by")
  private Users createdBy;
  @ManyToOne
  @JoinColumn(name = "updated_by")
  private Users updatedBy;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

    public Category(String categoryName, String description, Users createdBy, Users updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.categoryName = categoryName;
        this.description = description;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Category() {

    }
}
