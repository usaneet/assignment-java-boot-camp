package com.jump.ecommerce.invoice;

import com.jump.ecommerce.purchase.order.PurchaseOrder;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    private String id;
    private ZonedDateTime invoiceDate;
    private ZonedDateTime invoiceDueDate;
    private String paymentChannel;
    private String payeeDetail;

    @OneToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;
}