package com.jump.ecommerce.payment;

import com.jump.ecommerce.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod findById(Long id){
        List<PaymentMethod> list = paymentMethodRepository.findAll();
        return paymentMethodRepository.findById(id).orElseThrow(()->new DataNotFoundException("payment not found"));
    }

    public PaymentMethod findByMerchantId(Integer id){
        return paymentMethodRepository.findByMerchantId(id).orElseThrow(()->new DataNotFoundException("merchant not found"));
    }

    public PaymentMethod save(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }
}
