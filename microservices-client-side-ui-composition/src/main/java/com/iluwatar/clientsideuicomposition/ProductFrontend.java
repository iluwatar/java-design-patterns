package com.iluwatar.clientsideuicomposition;

import java.util.Map;

/**
 * ProductFrontend is a concrete implementation of FrontendComponent
 * that simulates fetching dynamic product data.
 */
public class ProductFrontend extends FrontendComponent {

  /**
   * Fetches a list of products based on dynamic parameters such as category.
   *
   * @param params parameters that influence the data fetched, e.g., "category"
   * @return a string representing a filtered list of products
   */
  @Override
  protected String getData(Map<String, String> params) {
    String category = params.getOrDefault("category", "all");
    return "Product List for category '"
        + category
        + "': [Product 1, Product 2, Product 3]";
  }
}
