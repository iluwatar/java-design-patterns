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
package com.iluwatar.corruption.system.modern;

import com.iluwatar.corruption.system.AntiCorruptionLayer;
import com.iluwatar.corruption.system.ShopException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class represents a modern shop system.
 * The main purpose of the class is to place orders and find orders.
 */
@Service
public class ModernShop {
  @Autowired
  private ModernStore store;

  @Autowired
  private AntiCorruptionLayer acl;

  /**
   * Places the order in the modern system.
   * If the order is already present in the legacy system, then no need to place it again.
   */
  public void placeOrder(ModernOrder order) throws ShopException {

    String id = order.getId();
    // check if the order is already present in the legacy system
    Optional<ModernOrder> orderInObsoleteSystem = acl.findOrderInLegacySystem(id);

    if (orderInObsoleteSystem.isPresent()) {
      var legacyOrder = orderInObsoleteSystem.get();
      if (!order.equals(legacyOrder)) {
        throw ShopException.throwIncorrectData(legacyOrder.toString(), order.toString());
      }
    } else {
      store.put(id, order);
    }
  }

  /**
   * Finds the order in the modern system.
   */
  public Optional<ModernOrder> findOrder(String orderId) {
    return store.get(orderId);
  }
}
