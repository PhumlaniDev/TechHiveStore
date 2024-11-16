package com.phumlanidev.techhivestore.model;

import com.phumlanidev.techhivestore.enums.OrderStatus;
import com.phumlanidev.techhivestore.enums.PaymentStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Comment: this is the placeholder for documentation.
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long orderId;
  @Column(name = "order_number")
  private UUID orderNumber;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userId; //foreign key
  @Column(name = "status")
  private OrderStatus orderStatus;
  @Column(name = "total_price")
  private double totalPrice;
  @Column(name = "payment_status")
  private PaymentStatus paymentStatus;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "address_id", referencedColumnName = "addressId")
  private Address addressId;
  @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> items;

}
