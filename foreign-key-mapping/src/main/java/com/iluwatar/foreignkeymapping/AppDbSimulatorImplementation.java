package com.iluwatar.foreignkeymapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppDbSimulatorImplementation implements AppDbSimulator {
  private List<Order> orderList = new ArrayList<>();
  private List<Person> personList = new ArrayList<>();

  public List<Order> getOrderList() {
    return orderList;
  }

  public void setOrderList(List<Order> orderList) {
    this.orderList = orderList;
  }

  public List<Person> getPersonList() {
    return personList;
  }

  public void setPersonList(List<Person> personList) {
    this.personList = personList;
  }

  @Override
  public Object find(int id, String table) throws IdNotFoundException {
    if (table.toLowerCase(Locale.ROOT).equals("person")) {
      for (Person elem : personList) {
        if (elem.getPersonNationalId() == id) {
          return elem;
        }
      }
      LOGGER.info("Person Not Found");
    } else if (table.toLowerCase(Locale.ROOT).equals("order")) {
      for (Order elem : orderList) {
        if (elem.getOrderNationalId() == id) {
          return elem;
        }
      }
      LOGGER.info("Person Not Found");
    } else {
      LOGGER.info("Table Not Found");

    }
    throw new IdNotFoundException("ID not in DataBase");
  }

  @Override
  public void insert(Object object, String table) {
    if (table.toLowerCase(Locale.ROOT).equals("person")) {
      Person person = (Person) object;
      for (Person elem : personList) {
        if (elem.getPersonNationalId() == person.getPersonNationalId()) {
          LOGGER.info("Record already exists.");
          return;
        }
      }
      personList.add(person);
    } else if (table.toLowerCase(Locale.ROOT).equals("order")) {
      Order order = (Order) object;
      boolean find = false;
      for (Person person : personList) {
        if (person.getPersonNationalId() == order.getPersonNationalId()) {
          find = true;
        }
      }
      if (find) {
        for (Order elem : orderList) {
          if (elem.getOrderNationalId() == order.getOrderNationalId()) {
            LOGGER.info("Record already exists.");
            return;
          }
        }
        orderList.add(order);
      } else {
        LOGGER.info("Person Not Found");
      }
    } else {
      LOGGER.info("Table Not Found");
    }
  }

  @Override
  public void update(Object object, String table) throws IdNotFoundException {
    if (table.toLowerCase(Locale.ROOT).equals("person")) {
      Person person = (Person) object;
      for (Person elem : personList) {
        if (elem.getPersonNationalId() == person.getPersonNationalId()) {
          elem.setFirstName(person.getFirstName());
          elem.setAge(person.getAge());
          LOGGER.info("Record updated successfully");
          return;
        }
      }
    } else if (table.toLowerCase(Locale.ROOT).equals("order")) {
      Order order = (Order) object;
      for (Order elem : orderList) {
        if (elem.getOrderNationalId() == order.getOrderNationalId()) {
          elem.setOrderNumber(order.getOrderNumber());
          elem.setPersonNationalId(order.getPersonNationalId());
          LOGGER.info("Record updated successfully");
          return;
        }
      }
    } else {
      LOGGER.info("Table Not Found");
    }

    throw new IdNotFoundException("ID not in DataBase");
  }

  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  public void delete(int id, String table) throws IdNotFoundException {
    if (table.toLowerCase(Locale.ROOT).equals("person")) {
      for (Person elem : personList) {
        if (elem.getPersonNationalId() == id) {
          personList.remove(elem);
          deletePersonOrder(id);
          LOGGER.info("Record deleted successfully.");
          return;
        }
      }
    } else if (table.toLowerCase(Locale.ROOT).equals("order")) {
      for (Order elem : orderList) {
        if (elem.getOrderNationalId() == id) {
          orderList.remove(elem);
          LOGGER.info("Record deleted successfully.");
          return;
        }
      }
    } else {
      LOGGER.info("Table Not Found");
    }

    throw new IdNotFoundException("ID : " + id + " not in DataBase");
  }

  /**
   * Multiple lines of Javadoc text are written here,
   * wrapped normally...
   */
  public void deletePersonOrder(int id) throws IdNotFoundException {
    for (Order elem : orderList) {
      if (elem.getPersonNationalId() == id) {
        orderList.remove(elem);
      }
    }
  }

}
