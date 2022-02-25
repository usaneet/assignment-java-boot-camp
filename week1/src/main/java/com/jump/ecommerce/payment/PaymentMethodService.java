package com.jump.ecommerce.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod findById(Long id){
        return paymentMethodRepository.getById(id);
    }
}
