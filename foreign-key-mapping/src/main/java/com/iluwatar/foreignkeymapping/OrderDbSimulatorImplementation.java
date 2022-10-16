package com.iluwatar.foreignkeymapping;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a sample database implementation. The database is in the form of an arraylist which stores records of
 * different persons. The personNationalId acts as the primary key for a record.
 * Operations :
 * -> find (look for object with a particular ID)
 * -> insert (insert record for a new person into the database)
 * -> update (update the record of a person). To do this, create a new person instance with the same ID as the record you
 *    want to update. Then call this method with that person as an argument.
 * -> delete (delete the record for a particular ID)
 */
@Slf4j
public class OrderDbSimulatorImplementation implements OrderDbSimulator {

  //    This simulates a database.
  private List<Order> orderList = new ArrayList<>();

  @Override
  public Order find(int orderNationalId) throws IdNotFoundException {
    for (Order elem : orderList) {
      if (elem.getOrderNationalId() == orderNationalId) {
        return elem;
      }
    }
    throw new IdNotFoundException("ID : " + orderNationalId + " not in DataBase");
  }

  @Override
  public void insert(Order order) {
    for (Order elem : orderList) {
      if (elem.getOrderNationalId() == order.getOrderNationalId()) {
        LOGGER.info("Record already exists.");
        return;
      }
    }
    orderList.add(order);
  }

  @Override
  public void update(Order order) throws IdNotFoundException {
    for (Order elem : orderList) {
      if (elem.getOrderNationalId() == order.getOrderNationalId()) {
        elem.setOrderNumber(order.getOrderNumber());
        elem.setPersonNationalId(order.getPersonNationalId());
        LOGGER.info("Record updated successfully");
        return;
      }
    }
    throw new IdNotFoundException("ID : " + order.getOrderNationalId() + " not in DataBase");
  }

  /**
   * Delete the record corresponding to given ID from the DB.
   *
   * @param id : personNationalId for person whose record is to be deleted.
   */
  public void delete(int id) throws IdNotFoundException {
    for (Order elem : orderList) {
      if (elem.getOrderNationalId() == id) {
        orderList.remove(elem);
        LOGGER.info("Record deleted successfully.");
        return;
      }
    }
    throw new IdNotFoundException("ID : " + id + " not in DataBase");
  }

  /**
   * Return the size of the database.
   */
  public int size() {
    if (orderList == null) {
      return 0;
    }
    return orderList.size();
  }
}