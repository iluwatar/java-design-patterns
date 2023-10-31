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
package com.iluwatar.messaging.model;

import java.util.List;

public class OrderRequest {

  private long consumerId;

  private long restaurantId;

  private List<MenuItemIdAndQuantity> menuItemIdAndQuantityList;

  public OrderRequest() {
    super();
  }

  public OrderRequest(long consumerId, long restaurantId, List<MenuItemIdAndQuantity> menuItemIdAndQuantityList) {
    this.consumerId = consumerId;
    this.restaurantId = restaurantId;
    this.menuItemIdAndQuantityList = menuItemIdAndQuantityList;
  }

  public long getConsumerId() {
    return consumerId;
  }

  public void setConsumerId(long consumerId) {
    this.consumerId = consumerId;
  }

  public long getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(long restaurantId) {
    this.restaurantId = restaurantId;
  }

  public List<MenuItemIdAndQuantity> getMenuItemIdAndQuantityList() {
    return menuItemIdAndQuantityList;
  }

  public void setMenuItemIdAndQuantityList(List<MenuItemIdAndQuantity> menuItemIdAndQuantityList) {
    this.menuItemIdAndQuantityList = menuItemIdAndQuantityList;
  }
}
