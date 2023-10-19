package com.iluwatar.messaging.model;

import java.util.List;

public class Order {

  private long orderId;

  private long consumerId;

  private long restaurantId;

  private List<MenuItemIdAndQuantity> menuItemIdAndQuantityList;

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
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

  public long getConsumerId() {
    return consumerId;
  }

  public void setConsumerId(long consumerId) {
    this.consumerId = consumerId;
  }

  public static OrderBuilder builder() {
    return new OrderBuilder();
  }
  public static final class OrderBuilder {
    private long orderId;
    private long consumerId;
    private long restaurantId;
    private List<MenuItemIdAndQuantity> menuItemIdAndQuantityList;

    private OrderBuilder() {
    }


    public OrderBuilder orderId(long orderId) {
      this.orderId = orderId;
      return this;
    }

    public OrderBuilder consumerId(long consumerId) {
      this.consumerId = consumerId;
      return this;
    }

    public OrderBuilder restaurantId(long restaurantId) {
      this.restaurantId = restaurantId;
      return this;
    }

    public OrderBuilder menuItemIdAndQuantityList(List<MenuItemIdAndQuantity> menuItemIdAndQuantityList) {
      this.menuItemIdAndQuantityList = menuItemIdAndQuantityList;
      return this;
    }

    public Order build() {
      Order order = new Order();
      order.setOrderId(orderId);
      order.setConsumerId(consumerId);
      order.setRestaurantId(restaurantId);
      order.setMenuItemIdAndQuantityList(menuItemIdAndQuantityList);
      return order;
    }
  }
}


