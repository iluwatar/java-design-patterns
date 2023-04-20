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
package com.iluwatar.embedded.value;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A POJO which represents the Order object.
 */
@ToString
@Setter
@Getter
public class Order {
  
  private int id;
  private String item;
  private String orderedBy;
  private ShippingAddress shippingAddress;
  
  /**
   * Constructor for Item object.
   * @param item item name
   * @param orderedBy item orderer
   * @param shippingAddress shipping address details
   */

  public Order(String item, String orderedBy, ShippingAddress shippingAddress) {
    this.item = item;
    this.orderedBy = orderedBy;
    this.shippingAddress = shippingAddress;
  }

  public Order(int id, String item, String orderedBy, ShippingAddress shippingAddress) {
    this(item, orderedBy, shippingAddress);
    this.id = id;
  }
  
}
