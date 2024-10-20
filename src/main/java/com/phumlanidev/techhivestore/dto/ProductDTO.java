package com.phumlanidev.techhivestore.dto;

import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.model.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p> comment </p>.
 */
@Data
public class ProductDTO {

  @NotBlank(message = "Name must not be blank")
  private String name;
  @NotBlank(message = "Description must not be blank")
  private String description;
  @NotBlank(message = "Price must not be blank")
  private String price;
  @NotNull(message = "Quantity must not be null")
  private Integer quantity;
  private String imageURL;
  @NotNull(message = "Category must not be null")
  private Category category;
  private Users createdByUser;
  private Users updatedByUser;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  public ProductDTO() {
  }

  public ProductDTO(LocalDateTime updatedDate,
                    LocalDateTime createdDate,
                    Users updatedByUser,
                    Users createdByUser,
                    Category category,
                    String imageURL,
                    Integer quantity,
                    String price,
                    String description,
                    String name) {
    this.updatedDate = updatedDate;
    this.createdDate = createdDate;
    this.updatedByUser = updatedByUser;
    this.createdByUser = createdByUser;
    this.category = category;
    this.imageURL = imageURL;
    this.quantity = quantity;
    this.price = price;
    this.description = description;
    this.name = name;
  }



}
