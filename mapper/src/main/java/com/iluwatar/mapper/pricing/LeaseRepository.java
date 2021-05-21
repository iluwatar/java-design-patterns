package com.iluwatar.mapper.pricing;

import com.iluwatar.mapper.Pricing;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>The repository for leasing. It records the information
 * of all commodities to be leased.</p>
 */
public final class LeaseRepository {

  /**
   * The repository for leasing.
   */
  private final transient List<Pricing> repository = new ArrayList<>();

  /**
   * Public constructor.
   */
  public LeaseRepository() {//NOPMD
    //Instantiate and do nothing.
  }

  /**
   * Query the repository.
   *
   * @return the query result.
   */
  public List<Pricing> query() {
    return repository;
  }

  /**
   * Find an object in the category.
   *
   * @param id the id of the object.
   * @return the object.
   */
  public Pricing findLease(final int id) {//NOPMD
    Pricing lease = null;//NOPMD
    for (final Pricing i :
            repository) {
      if (i.getId() == id) {
        lease = i;
        break;
      }
    }
    return lease;
  }
}
