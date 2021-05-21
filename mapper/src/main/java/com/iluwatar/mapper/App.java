package com.iluwatar.mapper;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Mapper pattern is to set up communications between two subsystems that still
 * need to stay ignorant of each other. {@link PricingMapper} is the communication
 * between {@link Customer}, {@link Lease}, {@link Asset} and
 * {@link com.iluwatar.mapper.pricing}.</p>
 */

@Slf4j
public class App {//NOPMD

  /**
   * Program entry point.
   *
   * @param args command line args
   */

  public static void main(final String[] args) {

    // Initialize system.
    final var pricingMapper = new PricingMapper();
    final var customer = new Customer(pricingMapper);
    final var lease = new Lease(pricingMapper);
    final var asset = new Asset(pricingMapper);

    // Instantiate a commodity.
    final var pricing = new Pricing("equipment", 100);

    // Configure the asset category and lease repository.
    asset.addObject(pricing);
    lease.addLease(pricing);
    asset.modifyObject("equipment", new Pricing("newEquipment", 50));
    lease.leaseObject(0);

    // Query the category and repository.
    final List<Pricing> category = customer.queryCategory();
    final List<Pricing> repository = customer.queryRepository();
    final List<Pricing> assetCategory = asset.queryCategory();
    final List<Pricing> leaseRepository = lease.queryLease();
    LOGGER.info("Customer objects category query result:");
    for (final Pricing i : category) {
      LOGGER.info("object:{} {}", i.getName(), i.getPrice());
    }
    LOGGER.info("Customer objects repository query result:");
    for (final Pricing i : repository) {
      LOGGER.info("object:{} {} {}", i.getName(), i.getPrice(), i.isLeased());
    }
    LOGGER.info("Asset query result:");
    for (final Pricing i : assetCategory) {
      LOGGER.info("object:{} {}", i.getName(), i.getPrice());
    }
    LOGGER.info("Lease query result:");
    for (final Pricing i : leaseRepository) {
      LOGGER.info("object:{} {} {}", i.getName(), i.getPrice(), i.isLeased());
    }

    // Modify the category and repository.
    lease.returnObject(0);
    lease.removeLease(0);
    asset.removeObject("newEquipment");
  }

}
