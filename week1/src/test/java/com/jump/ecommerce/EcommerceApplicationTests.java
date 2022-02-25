package com.jump.ecommerce;

import com.jump.ecommerce.customer.shipping.ShippingAddress;
import com.jump.ecommerce.invoice.Invoice;
import com.jump.ecommerce.payment.PaymentMethod;
import com.jump.ecommerce.payment.PaymentMethodService;
import com.jump.ecommerce.product.Product;
import com.jump.ecommerce.purchase.order.PurchaseOrder;
import com.jump.ecommerce.purchase.product.PurchaseProduct;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EcommerceApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Test
	@Order(1)
	@Description("List all product")
	void searchAllProduct() {
		ResponseEntity<List<Product>> productResponse = testRestTemplate.exchange(
				"/v1/products",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Product>>() { });
		List<Product> products = productResponse.getBody();
		assertEquals(3, products.size());
	}

	@Test
	@Order(2)
	@Description("Search product")
	void searchProductByName() {
		ResponseEntity<List<Product>> productResponse = testRestTemplate.exchange(
				"/v1/products?search=NMD",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Product>>() { });
		List<Product> products = productResponse.getBody();
		assertEquals(2, products.size());
	}

	@Test
	@Order(3)
	@Description("Get product, selected product")
	void getProduct() {
		Product productResponse = testRestTemplate.getForObject(
				"/v1/products/1",
				Product.class);
		assertEquals("399.00", String.valueOf(productResponse.getPrice()));
	}

	@Test
	@Order(4)
	@Description("Add product to the cart, and check the product size in the cart")
	void addProductToBasket(){
		//add product
		Product productResponse = testRestTemplate.getForObject("/v1/products/1", Product.class);
		PurchaseProduct purchaseProduct = new PurchaseProduct();
		purchaseProduct.setProduct(productResponse);
		purchaseProduct.setAmount(1);
		purchaseProduct.setPrice(productResponse.getPrice());

		String responseEntity = testRestTemplate.postForObject("/v1/carts/products", purchaseProduct, String.class);
		PurchaseOrder po = testRestTemplate.getForObject("/v1/carts", PurchaseOrder.class);
		assertEquals(1, po.getProducts().size());
	}

	@Test
	@Order(5)
	@Description("Add shipping address to cart prepare for invoice")
	void addShippingAddress(){
		//add shipping to purchase
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setAddress("1/7 sukumvit rd.");
		shippingAddress.setFullName("Usanee T.");
		shippingAddress.setEmail("capercom@gmail.com");
		shippingAddress.setPostalCode("10250");
		String responseEntity = testRestTemplate.postForObject("/v1/carts/shippingaddress", shippingAddress, String.class);
		PurchaseOrder po = testRestTemplate.getForObject("/v1/carts", PurchaseOrder.class);
		assertEquals("10250", po.getShippingAddress().getPostalCode());
	}

	@Test
	@Order(6)
	void purchase(){
		//add payment to purchase
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setMerchantId(20);
		String responseEntity = testRestTemplate.postForObject("/v1/carts/payment", paymentMethod, String.class);
		PurchaseOrder po = testRestTemplate.getForObject("/v1/carts", PurchaseOrder.class);
		PaymentMethod poPayment = paymentMethodService.findById(po.getPaymentMethod());
		assertEquals("cash", poPayment.getPaymentChannel());
	}

	@Test
	@Order(7)
	void getInvoice(){
		//do order
		PurchaseOrder po = testRestTemplate.getForObject("/v1/carts", PurchaseOrder.class);
		Invoice invoice = testRestTemplate.getForObject("/v1/carts/confirmorder", Invoice.class);
		Invoice invoiceDetail = testRestTemplate.getForObject("/v1/invoices/"+invoice.getInvoiceCode(), Invoice.class);
		assertEquals("cash", invoiceDetail.getPaymentChannel());
	}

}

