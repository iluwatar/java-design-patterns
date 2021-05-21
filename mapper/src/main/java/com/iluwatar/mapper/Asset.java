package com.iluwatar.mapper;

import java.util.List;

/**
 * <p>Asset class can modify the commodities category. It can add, remove
 * and modify commodities in the categories.</p>
 */
public final class Asset {

  /**
   * The corresponding pricingMapper.
   */
  private final transient PricingMapper pricingMapper;

  /**
   * Public constructor.
   */
  public Asset(final PricingMapper pricingMapper) {
    this.pricingMapper = pricingMapper;
  }

  /**
   * Add object to the category.
   *
   * @param pricing       the object to be add.
   * @return whether it succeeds.
   */
  public boolean addObject(final Pricing pricing) {
    return pricingMapper.addObject(pricing);
  }

  /**
   * Remove object to the category.
   *
   * @param name          the object to be removed.
   * @return whether it succeeds.
   */
  public boolean removeObject(final String name) {
    return pricingMapper.removeObject(name);
  }

  /**
   * Modify the object to the category.
   *
   * @param name          the object name to be modified.
   * @param newPricing    new asset of this object.
   * @return whether it succeeds.
   */
  public boolean modifyObject(final String name, final Pricing newPricing) {
    return pricingMapper.modifyObject(name, newPricing);
  }

  /**
   * Query the objects in the category.
   *
   * @return the query result.
   */
  public List<Pricing> queryCategory() {
    return pricingMapper.queryCategory();
  }
}
