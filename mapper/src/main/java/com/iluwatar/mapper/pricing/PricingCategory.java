package com.iluwatar.mapper.pricing;

import com.iluwatar.mapper.Pricing;
import java.util.ArrayList;

/**
 * <p>This class has records for all categories of the commodities.</p>
 */
public final class PricingCategory {

  /**
   * The array of all commodities.
   */
  private final ArrayList<Pricing> prices = new ArrayList<>();

  /**
   * Public constructor.
   */
  public PricingCategory() {
    //Instantiate and do nothing.
  }

  /**
   * Query the categories.
   *
   * @return the query result.
   */
  public ArrayList<Pricing> query() {
    return prices;
  }

  /**
   * Find an object in the category.
   *
   * @param name the name of the object.
   * @return the object.
   */
  public Pricing findObject(String name) {
    for (Pricing i : prices) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}
