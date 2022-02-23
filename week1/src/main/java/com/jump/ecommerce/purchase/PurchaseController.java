package com.jump.ecommerce.purchase;

import com.jump.ecommerce.product.Product;
import com.jump.ecommerce.purchase.order.PurchaseOrder;
import com.jump.ecommerce.purchase.order.PurchaseOrderService;
import com.jump.ecommerce.purchase.product.PurchaseProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/v1/carts")
public class PurchaseController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/products")
    public void addProduct(@RequestBody PurchaseProduct product) {
        Long customerId = Long.getLong("1"); //get customer id from context, extracted from token
        purchaseOrderService.addProduct(customerId, product);
    }

    @PutMapping("/products")
    public void updateProduct(@RequestBody PurchaseProduct product) {
        Long customerId = Long.getLong("1"); //get customer id from context, extracted from token
        purchaseOrderService.updateProduct(customerId, product);
    }

    @GetMapping("/products")
    public PurchaseOrder getPurchaseOrder(){
        Long customerId = Long.getLong("1");
        return purchaseOrderService.getPurchaseOrderByCustomerId(customerId);
    }

}
