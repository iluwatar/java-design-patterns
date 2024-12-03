package com.iluwatar.monolithic.controller;

import com.iluwatar.monolithic.model.Products;
import com.iluwatar.monolithic.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCon {
    @Autowired
    private ProductRepo productRepository;

    public Products addProduct(Products product) {
        return productRepository.save(product);
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }
}
