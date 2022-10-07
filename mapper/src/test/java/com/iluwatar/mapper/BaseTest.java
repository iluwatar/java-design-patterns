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

import com.iluwatar.mapper.pricing.PricingMapper;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public abstract class BaseTest {
  protected static Stream<Arguments> pricingTestCases() {
    return Stream.of(
        Arguments.of(
            new Customer(randomUUID().toString(), "Leanord"),
            new Asset(randomUUID().toString(), 200.0)),
        Arguments.of(
            new Customer(randomUUID().toString(), "Sheldon"),
            new Asset(randomUUID().toString(), 1200.0)),
        Arguments.of(
            new Customer(randomUUID().toString(), "Penny"),
            new Asset(randomUUID().toString(), 600.0)),
        Arguments.of(
            new Customer(randomUUID().toString(), "Raj"),
            new Asset(randomUUID().toString(), 700.0)));
  }

  protected static PricingMapper getPricingMapper(Customer customer, Asset asset) {
    Lease lease = new Lease(asset.getAssetId(), customer.getCustomerId());
    return new PricingMapper(customer, asset, lease);
  }
}
