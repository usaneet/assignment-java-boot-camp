package com.jump.ecommerce.customer.shipping;

import com.jump.ecommerce.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressService {

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    public void save(ShippingAddress shippingAddress){
        shippingAddressRepository.save(shippingAddress);
    }

    public ShippingAddress findById(Long id) {
        return shippingAddressRepository.findById(id).orElseThrow(() -> new DataNotFoundException("shippng address not found"));
    }

}
