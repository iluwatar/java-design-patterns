/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.layers.service;

import com.iluwatar.layers.dto.CakeInfo;
import com.iluwatar.layers.dto.CakeLayerInfo;
import com.iluwatar.layers.dto.CakeToppingInfo;
import com.iluwatar.layers.exception.CakeBakingException;
import java.util.List;

/**
 * Service for cake baking operations.
 */
public interface CakeBakingService {

  /**
   * Bakes new cake according to parameters.
   */
  void bakeNewCake(CakeInfo cakeInfo) throws CakeBakingException;

  /**
   * Get all cakes.
   */
  List<CakeInfo> getAllCakes();

  /**
   * Store new cake topping.
   */
  void saveNewTopping(CakeToppingInfo toppingInfo);

  /**
   * Get available cake toppings.
   */
  List<CakeToppingInfo> getAvailableToppings();

  /**
   * Add new cake layer.
   */
  void saveNewLayer(CakeLayerInfo layerInfo);

  /**
   * Get available cake layers.
   */
  List<CakeLayerInfo> getAvailableLayers();
}
