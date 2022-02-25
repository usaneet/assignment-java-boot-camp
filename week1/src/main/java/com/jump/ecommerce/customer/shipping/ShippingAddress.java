package com.jump.ecommerce.customer.shipping;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shipping_address")
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String fullName;
    private String address;
    private String postalCode;
    private String city;
    private String telephoneNumber;
    private Long customerId;
}
