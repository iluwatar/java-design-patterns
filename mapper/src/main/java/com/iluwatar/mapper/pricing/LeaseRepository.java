package com.iluwatar.mapper.pricing;

import com.iluwatar.mapper.Pricing;
import java.util.ArrayList;

/**
 * <p>The repository for leasing. It records the information of all commodities to be leased.</p>
 */
public final class LeaseRepository {

  /**
   * The repository for leasing.
   */
  private final ArrayList<Pricing> repository = new ArrayList<>();

  /**
   * Public constructor.
   */
  public LeaseRepository() {
    //Instantiate and do nothing.
  }

  /**
   * Query the repository.
   *
   * @return the query result.
   */
  public ArrayList<Pricing> query() {
    return repository;
  }

  /**
   * Find an object in the category.
   *
   * @param id the id of the object.
   * @return the object.
   */
  public Pricing findLease(int id) {
    for (Pricing i :
            repository) {
      if (i.getId() == id) {
        return i;
      }
    }
    return null;
  }
}
