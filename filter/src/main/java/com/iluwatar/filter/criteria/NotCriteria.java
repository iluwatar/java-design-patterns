package com.iluwatar.filter.criteria;

import com.iluwatar.filter.product.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Class definition for NotCriteria.
 * @param exclusions the list of criteria that must NOT be met
 */
public class NotCriteria implements Criteria<Product> {
  List<Criteria> exclusions;

  /**
   * Constructor for NotCriteria.
   */
  public NotCriteria(List<Criteria> exclusions) {
    this.exclusions = exclusions;
  }

  /**
   * Overrides Criteria interface method meetCriteria.
   * @param items the list of items to filter
   * @return a list of items that must NOT meet ANY of the criteria
   */
  @Override
  public List<Product> meetCriteria(List<Product> items) {

    List<Product> meetNone = items;

    for (Criteria criterion : exclusions) {
      List<Product> met = criterion.meetCriteria(items);
      for (Product p : met) {
        meetNone.remove(p);
      }
    }
    
    return meetNone;
  }
}