package com.iluwatar.filter.criteria;

import com.iluwatar.filter.product.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for low price criteria.
 */
public class LowPriceCriteria implements Criteria<Product> {
  /**
   * Overrides the meetCriteria from the criteria class
   * filters out products that meet the criteria, in this case, low price,
   * i.e. price < 200.
   * 
   * @param items list of items to filter
   * @return the list of items that meet the price criteria
   */
  @Override
  public List<Product> meetCriteria(List<Product> items) {
    List<Product> products = new ArrayList<Product>();
    for (Product product : items) {
      if (product.getPrice() < 200) {
        products.add(product);
      }
    }

    return products;
  }
}
