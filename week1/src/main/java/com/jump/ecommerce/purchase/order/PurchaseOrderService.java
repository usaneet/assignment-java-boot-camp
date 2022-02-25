package com.jump.ecommerce.purchase.order;

import com.jump.ecommerce.customer.Customer;
import com.jump.ecommerce.customer.CustomerRepository;
import com.jump.ecommerce.customer.CustomerService;
import com.jump.ecommerce.customer.shipping.ShippingAddress;
import com.jump.ecommerce.exception.DataNotFoundException;
import com.jump.ecommerce.purchase.product.PurchaseProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

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
            return newPurchaseOrder;
        } else {
            return purchaseOrder.get();
        }
    }

    public void addProduct(Long customerId, PurchaseProduct purchaseProduct){
        PurchaseOrder purchaseOrder = this.getPurchaseOrderByCustomerId(customerId);
        purchaseOrder.getProducts().add(purchaseProduct);
    }

    public void updateProduct(Long customerId, PurchaseProduct purchaseProduct){
        PurchaseOrder purchaseOrder = this.getPurchaseOrderByCustomerId(customerId);
        purchaseOrder.getProducts().stream().forEach(product -> {
            //update purchase product amount
            if(product.getProduct().getId() == purchaseProduct.getProduct().getId()) {
                product.setAmount(purchaseProduct.getAmount());
            }
        });
    }

    public void deleteProduct(Long customerId, PurchaseProduct purchaseProduct){
        PurchaseOrder purchaseOrder = this.getPurchaseOrderByCustomerId(customerId);
        purchaseOrder.getProducts().removeIf(product ->
            product.getId() == purchaseProduct.getId()
        );
    }

    public void updateShippingAddress(Long customerId, ShippingAddress shippingAddress){
        PurchaseOrder purchaseOrder = this.getPurchaseOrderByCustomerId(customerId);
        purchaseOrder.setShippingAddress(shippingAddress);
    }
}
