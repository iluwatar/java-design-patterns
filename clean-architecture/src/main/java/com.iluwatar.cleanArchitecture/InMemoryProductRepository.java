package com.iluwatar.cleanArchitecture;

import java.util.HashMap;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
  private final Map<String, Product> products = new HashMap<>();

  public InMemoryProductRepository() {
    products.put("1", new Product("1", "Laptop", 1000.0));
    products.put("2", new Product("2", "Smartphone", 500.0));
  }

  @Override
  public Product getProductById(String productId) {
    return products.get(productId);
  }
}
