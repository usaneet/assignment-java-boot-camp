package com.jump.ecommerce.brand;

import com.jump.ecommerce.seller.Seller;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
}
