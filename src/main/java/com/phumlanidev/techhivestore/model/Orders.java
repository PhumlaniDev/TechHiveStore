package com.phumlanidev.techhivestore.model;

import com.phumlanidev.techhivestore.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Data
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long orderId;
  @Column(name = "order_number")
  private UUID orderNumber;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userId; //foreign key
  @Column(name = "order_created_date")
  private LocalDateTime orderCreatedDate;
  @Column(name = "status")
  private OrderStatus status;
  @Column(name = "total_price")
  private double totalPrice;
  @Column(name = "payment_status")
  private PaymentStatus paymentStatus;

}
