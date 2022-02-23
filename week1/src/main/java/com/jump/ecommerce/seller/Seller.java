package com.jump.ecommerce.seller;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String location;
}
