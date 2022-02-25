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
    private int merchantId = 20;
    private String merchantName = "Counter Service Co.,Ltd";
    private int dueDate = 2;
    private String paymentChannel = "cash";
}
