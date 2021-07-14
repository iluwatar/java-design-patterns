/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.mapper;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Mapper pattern is to set up communications between two subsystems that
 * still need to stay ignorant of each other.</p>
 *
 * <p>{@link PricingMapper} is the communication between {@link Customer},
 * {@link Lease}, {@link Asset} and {@link com.iluwatar.mapper.pricing}.</p>
 *
 * <p>{@link Customer} is the customer to query the commodities.</p>
 *
 * <p>{@link Lease} is to configure the leasing process.</p>
 *
 * <p>{@link Asset} is to configure the asset of commodities.</p>
 *
 * <p>{@link com.iluwatar.mapper.pricing} is to record the assets of commodities
 * and the lease repository of the commodities.</p>
 */

@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */

  public static void main(String[] args) {

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
    final ArrayList<Pricing> category = customer.queryCategory();
    final ArrayList<Pricing> repository = customer.queryRepository();
    final ArrayList<Pricing> assetCategory = asset.queryCategory();
    final ArrayList<Pricing> leaseRepository = lease.queryLease();
    LOGGER.info("Customer objects category query result:");
    for (Pricing i : category) {
      LOGGER.info("object:{} {}", i.getName(), i.getPrice());
    }
    LOGGER.info("Customer objects repository query result:");
    for (Pricing i : repository) {
      LOGGER.info("object:{} {} {}", i.getName(), i.getPrice(), i.getLeased());
    }
    LOGGER.info("Asset query result:");
    for (Pricing i : assetCategory) {
      LOGGER.info("object:{} {}", i.getName(), i.getPrice());
    }
    LOGGER.info("Lease query result:");
    for (Pricing i : leaseRepository) {
      LOGGER.info("object:{} {} {}", i.getName(), i.getPrice(), i.getLeased());
    }

    // Modify the category and repository.
    lease.returnObject(0);
    lease.removeLease(0);
    asset.removeObject("newEquipment");
  }

}
