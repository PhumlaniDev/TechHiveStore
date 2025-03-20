package com.phumlanidev.techhivestore.utils;

import com.phumlanidev.techhivestore.model.Product;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/**
 * Comment: this is the placeholder for documentation.
 */
public class ProductSpecification {

  private ProductSpecification() {
  }

  /**
   * Comment: this is the placeholder for documentation.
   */
  public static Specification<Product> filterProducts(String name, String category,
                                                      BigDecimal minPrice, BigDecimal maxPrice) {

    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (name != null && !name.isEmpty()) {
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
            "%" + name.toLowerCase() + "%"));
      }

      if (category != null && !category.isEmpty()) {
        predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("category")),
            category.toLowerCase()));
      }

      if (minPrice != null) {
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
      }

      if (maxPrice != null) {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
