package com.iluwatar.mapper.pricing;

import com.iluwatar.mapper.Pricing;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This class has records for all categories of the commodities.</p>
 */
public final class PricingCategory {

  /**
   * The array of all commodities.
   */
  private final transient List<Pricing> prices = new ArrayList<>();

  /**
   * Public constructor.
   */
  public PricingCategory() {//NOPMD
    //Instantiate and do nothing.
  }

  /**
   * Query the categories.
   *
   * @return the query result.
   */
  public List<Pricing> query() {
    return prices;
  }

  /**
   * Find an object in the category.
   *
   * @param name the name of the object.
   * @return the object.
   */
  public Pricing findObject(final String name) {
    Pricing object = null;//NOPMD
    for (final Pricing i : prices) {
      if (i.getName().equals(name)) {
        object = i;//NOPMD
      }
    }
    return object;
  }
}
