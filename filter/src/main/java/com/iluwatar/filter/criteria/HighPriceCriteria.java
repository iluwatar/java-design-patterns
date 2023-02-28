package com.iluwatar.filter.criteria;

import com.iluwatar.filter.product.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for low price criteria.
 */
public class HighPriceCriteria implements Criteria<Product> {
  /**
   * Overrides the meetCriteria from the criteria class
   * filters out products that meet the criteria, in this case, high price,
   * i.e. price > 1000.
   * 
   * @param items list of items to filter
   * @return the list of items that meet the price criteria
   */
  @Override
  public List<Product> meetCriteria(List<Product> items) {
    List<Product> products = new ArrayList<Product>();
    for (Product product : items) {
      if (product.getPrice() < 1000) {
        products.add(product);
      }
    }

    return products;
  }
}
