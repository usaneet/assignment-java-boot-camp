package com.jump.ecommerce.product;

import com.jump.ecommerce.brand.Brand;
import com.jump.ecommerce.seller.Seller;
import lombok.Data;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String currency;
    private String images;
    private String warrantyType;
    private String warrantyDuration;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
