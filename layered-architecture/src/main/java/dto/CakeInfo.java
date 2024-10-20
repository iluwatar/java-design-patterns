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

package dto;

import java.util.List;

/**
 * DTO for cakes.
 */
public class CakeInfo {

  public final Long id;
  public final CakeToppingInfo cakeToppingInfo;
  public final List<CakeLayerInfo> cakeLayerInfos;

  /**
   * Constructor.
   */
  public CakeInfo(Long id, CakeToppingInfo cakeToppingInfo, List<CakeLayerInfo> cakeLayerInfos) {
    this.id = id;
    this.cakeToppingInfo = cakeToppingInfo;
    this.cakeLayerInfos = cakeLayerInfos;
  }

  /**
   * Constructor.
   */
  public CakeInfo(CakeToppingInfo cakeToppingInfo, List<CakeLayerInfo> cakeLayerInfos) {
    this.id = null;
    this.cakeToppingInfo = cakeToppingInfo;
    this.cakeLayerInfos = cakeLayerInfos;
  }

  /**
   * Calculate calories.
   */
  public int calculateTotalCalories() {
    var total = cakeToppingInfo != null ? cakeToppingInfo.calories : 0;
    total += cakeLayerInfos.stream().mapToInt(c -> c.calories).sum();
    return total;
  }

  @Override
  public String toString() {
    return String.format("CakeInfo id=%d topping=%s layers=%s totalCalories=%d", id,
        cakeToppingInfo, cakeLayerInfos, calculateTotalCalories());
  }
}
