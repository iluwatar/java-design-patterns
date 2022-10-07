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
package com.iluwatar.mapper.repository;

import com.iluwatar.mapper.Lease;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Repository containing information about the leased assets. */
public final class LeaseRepository {
  private final Map<String, Lease> leases;

  public LeaseRepository() {
    this.leases = new HashMap<>();
  }

  /**
   * Add a leased asset.
   *
   * @param lease asset's lease information
   */
  public void add(Lease lease) {
    leases.put(getKey(lease), lease);
  }

  /**
   * Remove a leased asset.
   *
   * @param lease asset's lease information
   */
  public void remove(Lease lease) {
    leases.remove(getKey(lease));
  }

  /**
   * Find all leased assets.
   *
   * @return a {@link Collection} of {@link Lease} leased assets
   */
  public Collection<Lease> getLeases() {
    return leases.values();
  }

  private static String getKey(Lease lease) {
    return String.format("%s|%s", lease.getAssetId(), lease.getCustomerId());
  }
}
