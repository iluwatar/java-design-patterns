package com.iluwatar.mapper;

import java.util.List;

/**
 * <p>Lease is the class to modify the repository information of commodities.
 * It can update the leasing information.</p>
 */
public final class Lease {

  /**
   * The corresponding pricingMapper.
   */
  private final transient PricingMapper pricingMapper;

  /**
   * Public constructor.
   */
  public Lease(final PricingMapper pricingMapper) {
    this.pricingMapper = pricingMapper;
  }

  /**
   * Lease a certain object.
   *
   * @param id            the object id to be leased.
   * @return whether it succeeds.
   */
  public boolean leaseObject(final int id) {//NOPMD
    return pricingMapper.leaseObject(id);
  }

  /**
   * Return a object.
   *
   * @param id            the object id to be returned.
   * @return whether is succeeds.
   */
  public boolean returnObject(final int id) {//NOPMD
    return pricingMapper.returnObject(id);
  }

  /**
   * Add a commodity for leasing in the repository.
   *
   * @param pricing       the commodity to be added.
   */
  public void addLease(final Pricing pricing) {
    pricingMapper.addLease(pricing);
  }

  /**
   * Remove a commodity for leasing in the repository.
   *
   * @param id            the commodity's id.
   * @return whether it succeeds.
   */
  public boolean removeLease(final int id) {//NOPMD
    return pricingMapper.removeLease(id);
  }

  /**
   * Query the repository.
   *
   * @return the query result.
   */
  public List<Pricing> queryLease() {
    return pricingMapper.queryRepository();
  }
}
