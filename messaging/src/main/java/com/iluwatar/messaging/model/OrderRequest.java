package com.iluwatar.messaging.model;

import java.util.List;

public class OrderRequest {

  private long consumerId;

  private long restaurantId;

  private List<MenuItemIdAndQuantity> menuItemIdAndQuantityList;

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
