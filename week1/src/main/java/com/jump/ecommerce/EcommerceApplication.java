package com.jump.ecommerce;

import com.jump.ecommerce.brand.Brand;
import com.jump.ecommerce.brand.BrandRepository;
import com.jump.ecommerce.customer.Customer;
import com.jump.ecommerce.customer.CustomerRepository;
import com.jump.ecommerce.product.Product;
import com.jump.ecommerce.product.ProductRepository;
import com.jump.ecommerce.seller.Seller;
import com.jump.ecommerce.seller.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Autowired
	ProductRepository productRepository;

	@Autowired
	BrandRepository brandRepository;

	@Autowired
	SellerRepository sellerRepository;

	@Autowired
	CustomerRepository customerRepository;

	@PostConstruct
	public void initData(){
		Seller pokaSeller = new Seller();
		pokaSeller.setName("Poca");
		pokaSeller.setLocation("Samutprakan");
		sellerRepository.save(pokaSeller);

		Brand pokaBrand = new Brand();
		pokaBrand.setName("Poca SHOE");
		brandRepository.save(pokaBrand);

		Product p1 = new Product();
		p1.setName("POCA SHOE NMD Sneakers");
		p1.setDescription("POCA SHOE NMD Sneakers description 1");
		p1.setPrice(BigDecimal.valueOf(399));
		p1.setCurrency("THB");
		p1.setBrand(pokaBrand);
		p1.setSeller(pokaSeller);
		productRepository.save(p1);

		Seller adidasSeller = new Seller();
		adidasSeller.setName("Super sport");
		adidasSeller.setLocation("Rayong");
		sellerRepository.save(adidasSeller);

		Brand adidas = new Brand();
		adidas.setName("Adidas");
		brandRepository.save(adidas);

		Product p2 = new Product();
		p2.setName("Adidas Yeezy Boot 350 V2");
		p2.setDescription("Adidas Yeezy Boot 350 V2 description 2");
		p2.setPrice(BigDecimal.valueOf(28900));
		p2.setCurrency("THB");
		p2.setBrand(adidas);
		p2.setSeller(adidasSeller);
		productRepository.save(p2);

		Product p3 = new Product();
		p3.setName("Adidas NMD");
		p3.setDescription("Adidas NMD description");
		p3.setPrice(BigDecimal.valueOf(9900));
		p3.setCurrency("THB");
		p3.setBrand(adidas);
		p3.setSeller(adidasSeller);
		productRepository.save(p3);

		Customer customer = new Customer();
		customer.setId(5);
		customer.setName("Usanee T.");
		customerRepository.save(customer);

	}

}
