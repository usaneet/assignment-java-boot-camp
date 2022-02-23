package com.jump.ecommerce.purchase.order;

import com.jump.ecommerce.customer.Customer;
import com.jump.ecommerce.product.Product;
import com.jump.ecommerce.purchase.product.PurchaseProduct;
import com.jump.ecommerce.shipping.ShippingAddress;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private List<PurchaseProduct> products;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;
}
