package com.jump.ecommerce.invoice;

import com.jump.ecommerce.exception.DataNotFoundException;
import com.jump.ecommerce.payment.PaymentMethod;
import com.jump.ecommerce.payment.PaymentMethodService;
import com.jump.ecommerce.purchase.order.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentMethodService paymentMethodService;

    public String crateInvoice(PurchaseOrder purchaseOrder) {
        PaymentMethod paymentMethod = paymentMethodService.findById(purchaseOrder.getPaymentMethod());
        Invoice invoice = new Invoice();
        invoice.setInvoiceCode(String.valueOf(ThreadLocalRandom.current().nextInt()));
        invoice.setInvoiceDate(ZonedDateTime.now());
        invoice.setInvoiceDueDate(ZonedDateTime.now().plusDays(paymentMethod.getDueDate()));
        invoice.setPayerDetail(purchaseOrder.getCustomer().getName());
        invoice.setPayeeDetail(paymentMethod.getMerchantName());
        return invoice.getInvoiceCode();
    }

    public Invoice getInvoiceDetail(String invoiceCode) {
        return invoiceRepository.findByInvoiceCode(invoiceCode).orElseThrow(()->new DataNotFoundException());
    }

}
