package com.jump.ecommerce.purchase.product;

import com.jump.ecommerce.product.Product;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "purchase_product")
public class PurchaseProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int amount;
    private BigDecimal price;
}
