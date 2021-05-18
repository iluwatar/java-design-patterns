package com.iluwatar.tabledatagateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Table Data Gateway is a design pattern in which an object acts as a gateway to a
 * database table. The idea is to separate the responsibility of fetching items from a
 * database from the actual usages of those objects. Users of the gateway are then insulated
 * from changes to the way objects are stored in the database.
 *
 * <p>In this example {@link PersonGateWay} contains methods that can be changed.
 * First the {@link PersonGateWay} insert some person and find them by name or id.
 * Then the {@link PersonGateWay} update the person and delete them.
 */
@Slf4j
public class App {
  /**
   * Entry point of the main program.
   *
   * @param args Program runtime arguments.
   */
  public static void main(String[] args) throws Exception {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Tony", "Stark", "M", 36);
    personGateWay.insert("Thor", "Odinson", "M", 40);
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    var firstNameSet = personGateWay.findByFirstName("Tony");
    if (firstNameSet != null) {
      LOGGER.info("Find person by name");
    }
    var personFindById = personGateWay.find(1);
    if (personFindById != null) {
      LOGGER.info("Find person by id");
    }
    personGateWay.update(1, "Thor", "Odinson", "M", 41);
    if (personGateWay.delete(0)) {
      LOGGER.info("delete successfully");
    } else {
      LOGGER.info("The input id is wrong!");
    }
  }
}
