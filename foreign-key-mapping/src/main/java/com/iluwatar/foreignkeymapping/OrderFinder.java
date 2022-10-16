package com.iluwatar.foreignkeymapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Any object of this class stores a DataBase and an Identity Map. When we try to look for a key we first check if
 * it has been cached in the Identity Map and return it if it is indeed in the map.
 * If that is not the case then go to the DataBase, get the record, store it in the
 * Identity Map and then return the record. Now if we look for the record again we will find it in the table itself which
 * will make lookup faster.
 */
@Slf4j
public class OrderFinder {
  //  Access to the Identity Map
  private OrderIdentityMap identityMap = new OrderIdentityMap();
  //  Access to the DataBase
  private OrderDbSimulatorImplementation db;
  /**
   * get person corresponding to input ID.
   *
   * @param key : personNationalId to look for.
   */
  public Order getOrder(int key) {
    // Try to find person in the identity map
    Order order = this.identityMap.getOrder(key);
    if (order != null) {
      LOGGER.info("Person found in the Map");
      return order;
    } else {
      // Try to find person in the database
      order = this.db.find(key);
      if (order != null) {
        this.identityMap.addOrder(order);
        LOGGER.info("Person found in DB.");
        return order;
      }
      LOGGER.info("Person with this ID does not exist.");
      return null;
    }
  }

  public OrderDbSimulatorImplementation getDB() {
    return db;
  }

  public OrderIdentityMap getIdentityMap() {
    return identityMap;
  }

  public void setDB(OrderDbSimulatorImplementation db) {
    this.db = db;
  }

  public void setIdentityMap(OrderIdentityMap identityMap) {
    this.identityMap = identityMap;
  }
}