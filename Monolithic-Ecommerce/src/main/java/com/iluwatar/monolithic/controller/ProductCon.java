package com.iluwatar.monolithic.controller;

import com.iluwatar.monolithic.model.Products;
import com.iluwatar.monolithic.repository.ProductRepo;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * ProductCon is a controller class for managing Product operations.
 * */


@Service
public class ProductCon {
  private final ProductRepo productRepository;
  /**
 * Linking Controller to DB.
 * */
  public ProductCon(ProductRepo productRepository) {
    this.productRepository = productRepository;
  }
  /**
 * Adds a product to the DB.
 * */
  public Products addProduct(Products product) {
    return productRepository.save(product);
  }
  /**
 * Returns all relevant Products.
 * */
  public List<Products> getAllProducts() {
    return productRepository.findAll();
  }
}
