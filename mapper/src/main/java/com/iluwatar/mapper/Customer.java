package com.iluwatar.mapper;

import java.util.ArrayList;

/**
 * <p>The customer operations are defined in this class. Customer can query
 * the commodities category and repository for leasing.</p>
 */
public final class Customer {

  /**
   * The corresponding pricingMapper.
   */
  private final PricingMapper pricingMapper;

  /**
   * Public constructor.
   */
  public Customer(PricingMapper pricingMapper) {
    this.pricingMapper = pricingMapper;
  }

  /**
   * Query the commodities category.
   *
   * @return query result.
   */
  public ArrayList<Pricing> queryCategory() {
    return pricingMapper.queryCategory();
  }

  /**
   * Query the repository for leasing.
   *
   * @return query result.
   */
  public ArrayList<Pricing> queryRepository() {
    return pricingMapper.queryRepository();
  }
}
