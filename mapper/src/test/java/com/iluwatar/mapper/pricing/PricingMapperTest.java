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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.mapper.Asset;
import com.iluwatar.mapper.BaseTest;
import com.iluwatar.mapper.Customer;
import com.iluwatar.mapper.Lease;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class PricingMapperTest extends BaseTest {
  @ParameterizedTest
  @MethodSource("pricingTestCases")
  void assignAsset(Customer customer, Asset asset) {
    PricingMapper pricingMapper = getPricingMapper(customer, asset);
    pricingMapper.assignAsset();
    assertTrue(pricingMapper.getCustomerAssets().contains(asset));
  }

  @ParameterizedTest
  @MethodSource("pricingTestCases")
  void getAssets(Customer customer, Asset asset) {
    PricingMapper pricingMapper = getPricingMapper(customer, asset);
    pricingMapper.assignAsset();
    assertTrue(pricingMapper.getAssets().contains(asset));
  }

  @ParameterizedTest
  @MethodSource("pricingTestCases")
  void getLeasedAssets(Customer customer, Asset asset) {
    PricingMapper pricingMapper = getPricingMapper(customer, asset);
    pricingMapper.assignAsset();
    assertTrue(
        pricingMapper.getLeasedAssets().stream()
            .map(Lease::getAssetId)
            .anyMatch(assetId -> assetId.equals(asset.getAssetId())));
  }

  @ParameterizedTest
  @MethodSource("pricingTestCases")
  void getPricing(Customer customer, Asset asset) {
    PricingMapper pricingMapper = getPricingMapper(customer, asset);
    pricingMapper.assignAsset();
    assertEquals(asset.getPrice(), pricingMapper.getPricing());
  }

  @ParameterizedTest
  @MethodSource("pricingTestCases")
  void getCustomerAssets(Customer customer, Asset asset) {
    PricingMapper pricingMapper = getPricingMapper(customer, asset);
    pricingMapper.assignAsset();
    assertTrue(pricingMapper.getCustomerAssets().contains(asset));
  }

  @ParameterizedTest
  @MethodSource("pricingTestCases")
  void revokeAsset(Customer customer, Asset asset) {
    PricingMapper pricingMapper = getPricingMapper(customer, asset);
    pricingMapper.assignAsset();
    assertTrue(pricingMapper.getCustomerAssets().contains(asset));
    pricingMapper.revokeAsset();
    assertFalse(pricingMapper.getCustomerAssets().contains(asset));
  }
}
