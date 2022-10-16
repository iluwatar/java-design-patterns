package com.iluwatar.foreignkeymapping;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * This class stores the map into which we will be caching records after loading them from a DataBase.
 * Stores the records as a Hash Map with the personNationalIDs as keys.
 */
@Slf4j
public class OrderIdentityMap {
  private HashMap<Integer, Order> orderMap = new HashMap<>();
  /**
   * Add person to the map.
   */
  public void addOrder(Order order) {
    if (!orderMap.containsKey(order.getOrderNationalId())) {
      orderMap.put(order.getOrderNationalId(), order);
    } else { // Ensure that addPerson does not update a record. This situation will never arise in our implementation. Added only for testing purposes.
      LOGGER.info("Key already in Map");
    }
  }

  /**
   * Get Person with given id.
   *
   * @param id : personNationalId as requested by user.
   */
  public Order getOrder(int id) {
    Order order = orderMap.get(id);
    if (order == null) {
      LOGGER.info("ID not in Map.");
    }
    return order;
  }

  /**
   * Get the size of the map.
   */
  public int size() {
    if (orderMap == null) {
      return 0;
    }
    return orderMap.size();
  }

  public HashMap<Integer, Order> getOrderMap() {
    return this.orderMap;
  }
}