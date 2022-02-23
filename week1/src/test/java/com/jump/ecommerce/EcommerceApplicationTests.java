package com.jump.ecommerce;

import com.jump.ecommerce.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EcommerceApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
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
	void getProduct() {
		Product productResponse = testRestTemplate.getForObject(
				"/v1/products/1",
				Product.class);
		assertEquals("399.00", String.valueOf(productResponse.getPrice()));
	}

	@Test
	void addProductToBasket(){
		//add product
		//basket size = 1
	}

	@Test
	void getBasket(){
		//basket size = 1
	}

	@Test
	void checkout(){
		//add customer to purchase
	}

	@Test
	void addShippingAddress(){
		//add shipping to purchase
	}

	@Test
	void purchase(){
		//add payment to purchase
		//create invoice
	}

	@Test
	void getInvoice(){
		//get invoice
	}

}

