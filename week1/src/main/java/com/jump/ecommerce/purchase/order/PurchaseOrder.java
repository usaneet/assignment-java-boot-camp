package com.jump.ecommerce.purchase.order;

import com.jump.ecommerce.customer.Customer;
import com.jump.ecommerce.purchase.product.PurchaseProduct;
import com.jump.ecommerce.customer.shipping.ShippingAddress;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private BigDecimal totalPrice;

  @OneToMany
  private List<PurchaseProduct> products = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "shipping_address_id")
  private ShippingAddress shippingAddress;

  private long paymentMethod;
}
