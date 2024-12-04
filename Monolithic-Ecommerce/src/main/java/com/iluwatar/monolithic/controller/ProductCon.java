package com.iluwatar.monolithic.controller;

import com.iluwatar.monolithic.model.Products;
import com.iluwatar.monolithic.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCon {
    private final ProductRepo productRepository;

  public ProductCon(ProductRepo productRepository) {
    this.productRepository = productRepository;
  }

  public Products addProduct(Products product) {
        return productRepository.save(product);
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }
}
