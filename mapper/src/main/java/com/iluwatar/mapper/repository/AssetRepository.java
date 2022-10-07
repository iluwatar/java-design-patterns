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

import com.iluwatar.mapper.Asset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Repository containing information about the assets registered. */
public final class AssetRepository {
  private final Map<String, Asset> assets;

  public AssetRepository() {
    this.assets = new HashMap<>();
  }

  /**
   * Add an asset.
   *
   * @param asset the asset details
   */
  public void add(Asset asset) {
    assets.put(asset.getAssetId(), asset);
  }

  /**
   * Remove an asset with identifier {@code id}.
   *
   * @param id the asset identifier
   */
  public void remove(String id) {
    assets.remove(id);
  }

  /**
   * Find all assets.
   *
   * @return a {@link Collection} of {@link Asset}
   */
  public Collection<Asset> getAssets() {
    return assets.values();
  }

  /**
   * Returns an {@link Asset} by param {@code id}.
   *
   * @param id id the asset identifier
   * @return the Asset
   */
  public Asset get(String id) {
    return assets.get(id);
  }
}
