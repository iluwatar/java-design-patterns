package com.iluwatar.mapper;

import com.iluwatar.mapper.pricing.LeaseRepository;
import com.iluwatar.mapper.pricing.PricingCategory;

import java.util.List;

/**
 * <p>The mapper for the communication of customer,
 * lease, asset and repository, category.</p>
 */
public final class PricingMapper {

  /**
   * The repository for leasing.
   */
  private final transient LeaseRepository leaseRepository;

  /**
   * The category of commodities.
   */
  private final transient PricingCategory pricingCategory;

  /**
   * Public constructor.
   */
  public PricingMapper() {
    leaseRepository = new LeaseRepository();
    pricingCategory = new PricingCategory();
  }

  /**
   * Query the category of commodities.
   *
   * @return the query result.
   */
  public List<Pricing> queryCategory() {
    return pricingCategory.query();
  }

  /**
   * Query the repository of commodities.
   *
   * @return the query result.
   */
  public List<Pricing> queryRepository() {
    return leaseRepository.query();
  }

  /**
   * Lease a certain object.
   *
   * @param id the object id to be leased.
   * @return whether it succeeds.
   */
  public boolean leaseObject(final int id) {//NOPMD
    final Pricing pricing = leaseRepository.findLease(id);
    boolean rtn;
    if (pricing == null || pricing.isLeased()) {
      rtn = false;
    } else {
      pricing.setLeased(true);
      rtn = true;
    }
    return rtn;
  }

  /**
   * Return a object.
   *
   * @param id the object id to be returned.
   * @return whether is succeeds.
   */
  public boolean returnObject(final int id) {//NOPMD
    final Pricing pricing = leaseRepository.findLease(id);
    boolean rtn;
    if (pricing == null || !pricing.isLeased()) {
      rtn = false;
    } else {
      pricing.setLeased(false);
      rtn = true;
    }
    return rtn;
  }

  /**
   * Add a commodity for leasing in the repository.
   *
   * @param pricing the commodity to be added.
   */
  public void addLease(final Pricing pricing) {
    final List<Pricing> repository = leaseRepository.query();
    for (int i = 0; i < repository.size(); i++) {
      if (repository.get(i).getId() != i) {
        repository.add(i, pricing);
        pricing.setId(i);
        return;
      }
    }
    pricing.setId(repository.size());
    repository.add(pricing);
  }

  /**
   * Remove a commodity for leasing in the repository.
   *
   * @param id the commodity's id.
   * @return whether it succeeds.
   */
  public boolean removeLease(final int id) {//NOPMD
    final List<Pricing> repository = leaseRepository.query();
    boolean rtn = false;//NOPMD
    for (int i = 0; i < repository.size(); i++) {
      if (repository.get(i).getId() == id) {
        repository.remove(i);
        rtn = true;
        break;
      }
    }
    return rtn;
  }

  /**
   * Add object to the category.
   *
   * @param pricing the object to be add.
   * @return whether it succeeds.
   */
  public boolean addObject(final Pricing pricing) {
    final List<Pricing> prices = pricingCategory.query();
    boolean rtn = true;//NOPMD
    for (final Pricing i :
            prices) {
      if (i.getName().equals(pricing.getName())) {
        rtn = false;//NOPMD
      }
    }
    if (rtn) {
      prices.add(pricing);
      for (final Pricing i :
              prices) {
        i.setId(prices.indexOf(i));
      }
    }
    return rtn;
  }

  /**
   * Remove object to the category.
   *
   * @param name the object to be removed.
   * @return whether it succeeds.
   */
  public boolean removeObject(final String name) {
    final List<Pricing> prices = pricingCategory.query();
    boolean rtn = false;//NOPMD
    for (int i = 0; i < prices.size(); i++) {
      if (prices.get(i).getName().equals(name)) {
        prices.remove(i);
        final List<Pricing> repository = leaseRepository.query();
        for (int j = 0; j < repository.size(); j++) {
          if (repository.get(j).getName().equals(name)) {
            repository.remove(j);
          }
        }
        rtn = true;
        break;
      }
    }
    return rtn;
  }

  /**
   * Modify the object to the category.
   *
   * @param name       the object name to be modified.
   * @param newPricing new asset of this object.
   * @return whether it succeeds.
   */
  public boolean modifyObject(final String name, final Pricing newPricing) {
    final Pricing pricing = pricingCategory.findObject(name);
    boolean rtn;
    if (pricing == null) {
      rtn = false;
    } else {
      pricing.setPrice(newPricing.getPrice());
      pricing.setName(newPricing.getName());
      final List<Pricing> repository = leaseRepository.query();
      for (final Pricing value : repository) {
        if (value.getName().equals(name)) {
          value.setPrice(newPricing.getPrice());
          value.setName(newPricing.getName());
        }
      }
      rtn = true;
    }
    return rtn;
  }
}
