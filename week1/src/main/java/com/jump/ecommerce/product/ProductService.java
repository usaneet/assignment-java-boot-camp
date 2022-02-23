package com.jump.ecommerce.product;

import com.jump.ecommerce.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

  private ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product getProductById(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new DataNotFoundException("Product not found"));
  }

  public List<Product> listProduct() {
    return productRepository.findAll();
  }

  public void createProduct(Product product) {
    productRepository.save(product);
  }

  public void updateProduct(Product product) {
    productRepository.findById(product.getId()).orElseThrow(() -> new DataNotFoundException());
    productRepository.save(product);
  }

  public void deleteProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
    productRepository.delete(product);
  }

  public List<Product> findByName(String name) {
    return productRepository.findByNameContaining(name);
  }
}
