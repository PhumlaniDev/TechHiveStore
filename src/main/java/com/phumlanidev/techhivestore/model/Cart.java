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
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long cartId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;
    private double totalPrice;
}