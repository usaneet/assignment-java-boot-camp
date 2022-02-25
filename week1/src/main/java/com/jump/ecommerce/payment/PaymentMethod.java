package com.jump.ecommerce.payment;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int merchantId;
    private String merchantName;
    private int dueDate;
    private String paymentChannel;
}
