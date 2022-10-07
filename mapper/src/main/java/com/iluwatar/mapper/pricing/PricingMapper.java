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
package com.iluwatar.mapper.pricing;

import com.iluwatar.mapper.Asset;
import com.iluwatar.mapper.Customer;
import com.iluwatar.mapper.Lease;
import com.iluwatar.mapper.repository.AssetRepository;
import com.iluwatar.mapper.repository.CustomerRepository;
import com.iluwatar.mapper.repository.LeaseRepository;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * The mapper responsible for mapping {@link Pricing} from {@link Asset}, {@link Customer}, and
 * {@link Lease}.
 */
@Slf4j
public final class PricingMapper {
  private static final AssetRepository assetRepository = new AssetRepository();
  private static final LeaseRepository leaseRepository = new LeaseRepository();
  private static final CustomerRepository customerRepository = new CustomerRepository();
  private final Customer customer;
  private final Asset asset;
  private final Lease lease;

  /**
   * Constructor initializing the {@link PricingMapper}.
   *
   * @param customer the customer who needs to lease the asset
   * @param asset the asset to be leased
   * @param lease the lease between customer and asset
   */
  public PricingMapper(Customer customer, Asset asset, Lease lease) {
    this.customer = customer;
    this.asset = asset;
    this.lease = lease;
  }

  /** Assigns an asset to a customer. */
  public void assignAsset() {
    customerRepository.add(customer);
    assetRepository.add(asset);
    leaseRepository.add(lease);
  }

  /**
   * Find all assets available.
   *
   * @return a {@link Collection} of assets
   */
  public Collection<Asset> getAssets() {
    return assetRepository.getAssets();
  }

  /**
   * Find all leased assets.
   *
   * @return a {@link Collection} of assets
   */
  public Collection<Lease> getLeasedAssets() {
    return leaseRepository.getLeases();
  }

  /**
   * Find the pricing information of the customer.
   *
   * @return Pricing information of the customer
   */
  public double getPricing() {
    return getCustomerAssets().stream().mapToDouble(Asset::getPrice).sum();
  }

  /**
   * Find all assets leased by the customer.
   *
   * @return a {@link List} of customer assets
   */
  public List<Asset> getCustomerAssets() {
    return leaseRepository.getLeases().stream()
        .filter(lease -> Objects.equals(lease.getCustomerId(), customer.getCustomerId()))
        .map(Lease::getAssetId)
        .map(assetRepository::get)
        .collect(Collectors.toList());
  }

  /** Revoke an asset from a customer. */
  public void revokeAsset() {
    leaseRepository.remove(lease);
    LOGGER.info("Revoked asset='{}' from customer='{}'", asset, customer);
  }
}
