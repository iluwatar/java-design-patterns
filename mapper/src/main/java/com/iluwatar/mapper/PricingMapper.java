package com.iluwatar.mapper;

import com.iluwatar.mapper.pricing.LeaseRepository;
import com.iluwatar.mapper.pricing.PricingCategory;
import java.util.ArrayList;

/**
 * <p>The mapper for the communication of customer, lease, asset and repository, category.</p>
 */
public final class PricingMapper {

  /**
   * The category of commodities and repository for leasing.
   */
  private final LeaseRepository leaseRepository;
  private final PricingCategory pricingCategory;

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
  public ArrayList<Pricing> queryCategory() {
    return pricingCategory.query();
  }

  /**
   * Query the repository of commodities.
   *
   * @return the query result.
   */
  public ArrayList<Pricing> queryRepository() {
    return leaseRepository.query();
  }

  /**
   * Lease a certain object.
   *
   * @param id the object id to be leased.
   * @return whether it succeeds.
   */
  public boolean leaseObject(int id) {
    Pricing pricing = leaseRepository.findLease(id);
    if (pricing == null || pricing.getLeased()) {
      return false;
    } else {
      pricing.setLeased(true);
      return true;
    }
  }

  /**
   * Return a object.
   *
   * @param id the object id to be returned.
   * @return whether is succeeds.
   */
  public boolean returnObject(int id) {
    Pricing pricing = leaseRepository.findLease(id);
    if (pricing == null || !pricing.getLeased()) {
      return false;
    } else {
      pricing.setLeased(false);
      return true;
    }
  }

  /**
   * Add a commodity for leasing in the repository.
   *
   * @param pricing the commodity to be added.
   */
  public void addLease(Pricing pricing) {
    ArrayList<Pricing> repository = leaseRepository.query();
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
  public boolean removeLease(int id) {
    ArrayList<Pricing> repository = leaseRepository.query();
    for (int i = 0; i < repository.size(); i++) {
      if (repository.get(i).getId() == id) {
        repository.remove(i);
        return true;
      }
    }
    return false;
  }

  /**
   * Add object to the category.
   *
   * @param pricing the object to be add.
   * @return whether it succeeds.
   */
  public boolean addObject(Pricing pricing) {
    ArrayList<Pricing> prices = pricingCategory.query();
    for (Pricing i :
            prices) {
      if (i.getName().equals(pricing.getName())) {
        return false;
      }
    }
    prices.add(pricing);
    for (int i = 0; i < prices.size(); i++) {
      prices.get(i).setId(i);
    }
    return true;
  }

  /**
   * Remove object to the category.
   *
   * @param name the object to be removed.
   * @return whether it succeeds.
   */
  public boolean removeObject(String name) {
    ArrayList<Pricing> prices = pricingCategory.query();
    for (int i = 0; i < prices.size(); i++) {
      if (prices.get(i).getName().equals(name)) {
        prices.remove(i);
        ArrayList<Pricing> repository = leaseRepository.query();
        for (int j = 0; j < repository.size(); j++) {
          if (repository.get(j).getName().equals(name)) {
            repository.remove(j);
          }
        }
        return true;
      }
    }
    return false;
  }

  /**
   * Modify the object to the category.
   *
   * @param name       the object name to be modified.
   * @param newPricing new asset of this object.
   * @return whether it succeeds.
   */
  public boolean modifyObject(String name, Pricing newPricing) {
    Pricing pricing = pricingCategory.findObject(name);
    if (pricing == null) {
      return false;
    } else {
      pricing.setPrice(newPricing.getPrice());
      pricing.setName(newPricing.getName());
      ArrayList<Pricing> repository = leaseRepository.query();
      for (Pricing value : repository) {
        if (value.getName().equals(name)) {
          value.setPrice(newPricing.getPrice());
          value.setName(newPricing.getName());
        }
      }
      return true;
    }
  }
}
