package com.jump.ecommerce.purchase;

import com.jump.ecommerce.customer.shipping.ShippingAddress;
import com.jump.ecommerce.customer.shipping.ShippingAddressService;
import com.jump.ecommerce.invoice.InvoiceService;
import com.jump.ecommerce.payment.PaymentMethod;
import com.jump.ecommerce.product.Product;
import com.jump.ecommerce.purchase.order.PurchaseOrder;
import com.jump.ecommerce.purchase.order.PurchaseOrderService;
import com.jump.ecommerce.purchase.product.PurchaseProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/v1/carts")
public class PurchaseController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Autowired
    private InvoiceService invoiceService;

    /**
     * Assume customerId get from context, extracted from token
     */
    private Long customerId = Long.getLong("1"); //get customer id from context, extracted from token

    @GetMapping
    public PurchaseOrder getPurchaseOrder(){
        return purchaseOrderService.getPurchaseOrderByCustomerId(customerId);
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody PurchaseProduct product) {
        purchaseOrderService.addProduct(customerId, product);
    }

    @PutMapping("/products")
    public void updateProduct(@RequestBody PurchaseProduct product) {
        purchaseOrderService.updateProduct(customerId, product);
    }

    @PostMapping("/shippingaddress")
    public void addShippingAddress(@RequestBody ShippingAddress shippingAddress) {
        shippingAddress.setCustomerId(customerId);
        shippingAddressService.save(shippingAddress);
        purchaseOrderService.updateShippingAddress(customerId, shippingAddress);
    }

    @PostMapping("/payment")
    public void addPaymentMethod(@RequestBody PaymentMethod paymentMethod, String voucherCode) {
        if(paymentMethod.getPaymentChannel().equals("cash")){
            purchaseOrderService.updatePayment(customerId, paymentMethod);
        }
        if( StringUtils.hasLength(voucherCode) ){
            //update PO with the discount price
        }
    }

    @PostMapping("/confirmorder")
    public String confirmOrder(){
        return invoiceService.crateInvoice(this.getPurchaseOrder());
    }
}
