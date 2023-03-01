package com.iluwatar.filter.criteria;

import com.iluwatar.filter.product.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for InStockCriteria.
 */
public class InStockCriteria implements Criteria<Product> {

  /**
   * Overrides method meetCriteria from interface Criteria.
   * Filters out items that are in stock.
   * 
   * @param items the list of items to filter
   * @return the list of items in stock
   */
  @Override
  public List<Product> meetCriteria(List<Product> items) {
    List<Product> inStockProducts = new ArrayList<>();
    for (Product product : items) {
      if (product.getNrInStock() > 0) {
        inStockProducts.add(product);
      }
    }
    return inStockProducts;
  }
}
