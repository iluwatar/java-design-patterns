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
package com.iluwatar.corruption.system;

import com.iluwatar.corruption.system.legacy.LegacyShop;
import com.iluwatar.corruption.system.modern.Customer;
import com.iluwatar.corruption.system.modern.ModernOrder;
import com.iluwatar.corruption.system.modern.Shipment;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class represents an anti-corruption layer.
 * The main purpose of the class is to provide a layer between the modern and legacy systems.
 * The class is responsible for converting the data from one system to another
 * decoupling the systems to each other
 *
 * <p>It allows using one system a domain model of the other system
 * without changing the domain model of the system.
 */
@Service
public class AntiCorruptionLayer {

  @Autowired
  private LegacyShop legacyShop;


  /**
   * The method converts the order from the legacy system to the modern system.
   * @param id the id of the order
   * @return the order in the modern system
   */
  public Optional<ModernOrder> findOrderInLegacySystem(String id) {

    return legacyShop.findOrder(id).map(o ->
        new ModernOrder(
            o.getId(),
            new Customer(o.getCustomer()),
            new Shipment(o.getItem(), o.getQty(), o.getPrice()),
            ""
        )
    );
  }

}
