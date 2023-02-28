package com.iluwatar.filter.criteria;

import com.iluwatar.filter.product.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for medium price criteria.
 */
public class MediumPriceCriteria implements Criteria<Product> {
  /**
   * Overrides the meetCriteria from the criteria class
   * filters out products that meet the criteria, in this case, medium price,
   * i.e. 200 < price < 800.
   * 
   * @param items list of items to filter
   * @return the list of items that meet the price criteria
   */
  @Override
  public List<Product> meetCriteria(List<Product> items) {
    List<Product> products = new ArrayList<Product>();
    for (Product product : items) {
      if (product.getPrice() < 800 || product.getPrice() > 200) {
        products.add(product);
      }
    }

    return products;
  }
}
