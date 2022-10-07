/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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

import static java.util.UUID.randomUUID;

import com.iluwatar.mapper.pricing.Pricing;
import com.iluwatar.mapper.pricing.PricingMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * A simple application explaining the mapper pattern.
 *
 * <p>Mapper pattern is useful when you need to set up communications between two or more subsystems
 * that still need to stay ignorant of each other.
 *
 * <p>There are three independent subsystems identified by {@link Customer}, {@link Lease} and
 * {@link Asset} in this application.
 *
 * <p>{@link Customer} the customer who needs to lease the asset.
 *
 * <p>{@link Lease} the lease between a customer and asset.
 *
 * <p>{@link Asset} the asset that can be leased.
 *
 * <p>{@link PricingMapper} the mapper which maps the {@link Pricing} with {@link Customer}, {@link
 * Lease}, and {@link Asset}.
 *
 * <p>{@link Pricing} the pricing system which calculates the cost of the {@link Customer}.
 */
@Slf4j
public final class LeaseManagementApp {
  /**
   * Application entry point.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    final var assetId = randomUUID().toString();
    final var customerId = randomUUID().toString();

    final var customer = new Customer(customerId, "John Doe");
    final var asset = new Asset(assetId, 1200.0);
    final var lease = new Lease(assetId, customerId);
    final var mapper = new PricingMapper(customer, asset, lease);
    final var pricing = new Pricing(mapper);

    mapper.assignAsset();
    LOGGER.debug("Assets: {}", mapper.getAssets());
    LOGGER.debug("Leases: {}", mapper.getLeasedAssets());
    LOGGER.debug("Assigned Assets: {}", mapper.getCustomerAssets());
    LOGGER.debug("Price Information: {}", pricing.findCustomerCost());

    mapper.revokeAsset();
    LOGGER.debug("Price Information: {}", pricing.findCustomerCost());
  }
}
