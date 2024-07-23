package com.phumlanidev.techhivestore.model;

import com.phumlanidev.techhivestore.enums.OrderStatus;
import com.phumlanidev.techhivestore.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 * <p> comment </p>.
 */
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
  private Users userId; //foreign key
  @Column(name = "order_created_date")
  private LocalDateTime orderCreatedDate;
  @Column(name = "status")
  private OrderStatus status;
  @Column(name = "total_price")
  private double totalPrice;
  @Column(name = "payment_status")
  private PaymentStatus paymentStatus;

}
