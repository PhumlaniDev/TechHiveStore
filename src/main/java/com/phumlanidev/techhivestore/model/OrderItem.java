package com.phumlanidev.techhivestore.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * <p> comment </p>.
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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