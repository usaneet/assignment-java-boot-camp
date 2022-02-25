package com.jump.ecommerce.purchase.order;

import com.jump.ecommerce.customer.Customer;
import com.jump.ecommerce.customer.CustomerRepository;
import com.jump.ecommerce.customer.CustomerService;
import com.jump.ecommerce.customer.shipping.ShippingAddress;
import com.jump.ecommerce.exception.DataNotFoundException;
import com.jump.ecommerce.payment.PaymentMethod;
import com.jump.ecommerce.payment.PaymentMethodService;
import com.jump.ecommerce.purchase.product.PurchaseProduct;
import com.jump.ecommerce.purchase.product.PurchaseProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseProductRepository purchaseProductRepository;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private CustomerService customerService;

    /**
     * Idea is 1 PO/cart per 1 customer at a time, then if this customer hasn't his PO system will create a new one
     * @param customerId
     * @return
     */
    public PurchaseOrder getPurchaseOrderByCustomerId(Long customerId){
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findByCustomerId(customerId);
        if(!purchaseOrder.isPresent()){
            Customer customer = customerService.findById(customerId).orElseThrow(() -> new DataNotFoundException());
            PurchaseOrder newPurchaseOrder = new PurchaseOrder();
            newPurchaseOrder.setCustomer(customer);
            purchaseOrderRepository.save(newPurchaseOrder);
            return newPurchaseOrder;
        } else {
            return purchaseOrder.get();
        }
    }

    public void addProduct(Long customerId, PurchaseProduct purchaseProduct){
        purchaseProductRepository.save(purchaseProduct);
        PurchaseOrder purchaseOrder = this.getPurchaseOrderByCustomerId(customerId);
        purchaseOrder.getProducts().add(purchaseProduct);
        purchaseOrderRepository.save(purchaseOrder);
    }

    public void updateProduct(Long customerId, PurchaseProduct purchaseProduct){
        purchaseProductRepository.save(purchaseProduct);
        PurchaseOrder purchaseOrder = this.getPurchaseOrderByCustomerId(customerId);
        purchaseOrder.getProducts().stream().forEach(product -> {
            //update purchase product amount
            if(product.getProduct().getId() == purchaseProduct.getProduct().getId()) {
                product.setAmount(purchaseProduct.getAmount());
            }
        });
        purchaseOrderRepository.save(purchaseOrder);
    }

    public void deleteProduct(Long customerId, PurchaseProduct purchaseProduct){
        PurchaseOrder purchaseOrder = this.getPurchaseOrderByCustomerId(customerId);
        purchaseOrder.getProducts().removeIf(product ->
            product.getId() == purchaseProduct.getId()
        );
        purchaseOrderRepository.save(purchaseOrder);
    }

    public void updateShippingAddress(Long customerId, ShippingAddress shippingAddress){
        PurchaseOrder purchaseOrder = this.getPurchaseOrderByCustomerId(customerId);
        purchaseOrder.setShippingAddress(shippingAddress);
        purchaseOrderRepository.save(purchaseOrder);
    }

    public void updatePayment(Long customerId, PaymentMethod paymentMethod){
        PaymentMethod payment = paymentMethodService.findByMerchantId(paymentMethod.getMerchantId());
        PurchaseOrder purchaseOrder = this.getPurchaseOrderByCustomerId(customerId);
        purchaseOrder.setPaymentMethod(payment.getId());
        purchaseOrderRepository.save(purchaseOrder);
    }
}
